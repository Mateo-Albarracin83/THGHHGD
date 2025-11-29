package com.alpescab.controller;

import com.alpescab.model.Servicio;
import com.alpescab.model.Vehiculo;
import com.alpescab.model.Usuario;
import com.alpescab.model.TarjetaCredito;
import com.alpescab.model.PuntoGeografico;
import com.alpescab.model.ServicioPunto;
import com.alpescab.model.Disponibilidad;
import com.alpescab.repository.ServicioRepository;
import com.alpescab.repository.VehiculoRepository;
import com.alpescab.repository.UsuarioRepository;
import com.alpescab.repository.TarjetaCreditoRepository;
import com.alpescab.repository.PuntoGeograficoRepository;
import com.alpescab.repository.ServicioPuntoRepository;
import com.alpescab.repository.DisponibilidadRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Comparator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.HashMap;
import java.util.Collections;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/servicios")
public class ServicioController {
    private static final Logger logger = LoggerFactory.getLogger(ServicioController.class);
    private final ServicioRepository servicioRepository;
    private final UsuarioRepository usuarioRepository;
    private final VehiculoRepository vehiculoRepository;
    private final TarjetaCreditoRepository tarjetaCreditoRepository;
    private final PuntoGeograficoRepository puntoGeograficoRepository;
    private final ServicioPuntoRepository servicioPuntoRepository;
    private final DisponibilidadRepository disponibilidadRepository;

    public ServicioController(ServicioRepository servicioRepository,
                              UsuarioRepository usuarioRepository,
                              VehiculoRepository vehiculoRepository,
                              TarjetaCreditoRepository tarjetaCreditoRepository,
                              PuntoGeograficoRepository puntoGeograficoRepository,
                              ServicioPuntoRepository servicioPuntoRepository,
                              DisponibilidadRepository disponibilidadRepository) {
        this.servicioRepository = servicioRepository;
        this.usuarioRepository = usuarioRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.tarjetaCreditoRepository = tarjetaCreditoRepository;
        this.puntoGeograficoRepository = puntoGeograficoRepository;
        this.servicioPuntoRepository = servicioPuntoRepository;
        this.disponibilidadRepository = disponibilidadRepository;
    }

    @GetMapping
    public String servicios(Model model) {
        List<Servicio> servicios = servicioRepository.findAll();
        model.addAttribute("servicios", servicios);
        return "servicios";
    }

    @GetMapping("/new")
    public String servicioForm(Model model) {
        model.addAttribute("servicio", new Servicio());
        model.addAttribute("vehicles", vehiculoRepository.findAll());
        model.addAttribute("users", usuarioRepository.findAll());
        model.addAttribute("puntos", puntoGeograficoRepository.findAll());
        model.addAttribute("tarjetas", tarjetaCreditoRepository.darTarjetaCredito());
        return "servicioNew";
    }

    /**
     * RF8 solicitar un servicio por parte de un usuario de servicios
     * 1) verificar tarjeta del cliente
     * 2) buscar disponibilidad (tipo + hora actual dentro del rango + fecha = hoy)
     * 3) eliminar esa Disponibilidad (lo marca efectivamente como asignada)
     * 4) crear y persistir Servicio + ServicioPunto(s)
     * Si algo falla -> rollback automático.
     */
    @PostMapping("/new/save")
    @Transactional(rollbackFor = Exception.class)
    public String guardarServicioTransactional(
            @RequestParam("tipo") String tipo,
            @RequestParam("distancia") Double distancia,
            @RequestParam("tarifa") Double tarifa,
            @RequestParam("puntoPartidaId") Long puntoPartidaId,
            @RequestParam("puntoDestinoId") Long puntoDestinoId,
            @RequestParam("clienteId") String clienteId,
            @RequestParam("tarjetaNumero") Long tarjetaNumero,
            RedirectAttributes redirectAttributes,
            Model model) {

        // hora de inicio será ahora
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();

        // 1) Verificar tarjeta del cliente
        List<TarjetaCredito> tarjetasCliente = tarjetaCreditoRepository.findByUsuario_Cedula(clienteId);
        if (tarjetasCliente == null || tarjetasCliente.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El cliente no tiene medio de pago registrado.");
            logger.warn("Intento de solicitar servicio sin tarjeta: clienteId={}", clienteId);
            return "redirect:/servicios/new";
        }
        Optional<TarjetaCredito> maybeTarjeta = tarjetaCreditoRepository.findById(tarjetaNumero);
        if (maybeTarjeta.isEmpty() || !maybeTarjeta.get().getUsuario().getCedula().equals(clienteId)) {
            redirectAttributes.addFlashAttribute("error", "Tarjeta seleccionada inválida para el cliente.");
            logger.warn("Tarjeta inválida seleccionada para cliente {}: tarjeta={}", clienteId, tarjetaNumero);
            return "redirect:/servicios/new";
        }

        // 2) Buscar disponibilidad válida: tipo del servicio, fecha = hoy, horaInicio <= now <= horaFin
        String tipoNormalized = (tipo == null) ? "" : tipo.trim().toUpperCase();
        Optional<Disponibilidad> maybeDisp = disponibilidadRepository.findFirstAvailableForTipoAndFechaAndTime(tipoNormalized, today, now);
        if (maybeDisp.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No hay conductores disponibles para el tipo/horario solicitado. Intente más tarde.");
            logger.info("No hay disponibilidad: tipo={} clienteId={} fecha={} hora={}", tipoNormalized, clienteId, today, now);
            return "redirect:/servicios/new";
        }
        Disponibilidad disp = maybeDisp.get();
        Vehiculo vehiculo = disp.getVehiculo();
        Usuario conductor = vehiculo.getConductor();

        // 3) "Reservar" la disponibilidad: la eliminamos para que no la pueda usar otro request.
        //    Esto se hace dentro de la transacción; si algo falla, la eliminación se revierte.
        disp.setAsignada(1); // o disp.setAsignadaBoolean(true);
        disponibilidadRepository.save(disp);

        // 4) Crear y persistir Servicio
        Servicio servicio = new Servicio();
        Long servicioId = servicioRepository.nextId();
        servicio.setIdServicio(servicioId);
        servicio.setTipo(tipo);
        servicio.setDistancia(distancia);
        servicio.setTarifa(tarifa);
        servicio.setHoraInicio(now);
        servicio.setVehiculo(vehiculo);
        Usuario cliente = usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalStateException("Cliente no encontrado"));
        servicio.setCliente(cliente);
        servicio.setConductor(conductor);

        // valor del servicio (simple): tarifa * distancia
        Double valorServicio = tarifa * distancia;
        servicioRepository.save(servicio);

        // crear puntos (partida=orden1, destino=orden2)
        PuntoGeografico partida = puntoGeograficoRepository.findById(puntoPartidaId)
                .orElseThrow(() -> new IllegalStateException("Punto partida no encontrado"));
        PuntoGeografico destino = puntoGeograficoRepository.findById(puntoDestinoId)
                .orElseThrow(() -> new IllegalStateException("Punto destino no encontrado"));

        ServicioPunto sp1 = new ServicioPunto();
        Long nextSpId = servicioPuntoRepository.nextId();
        if (nextSpId == null) {
            throw new IllegalStateException("No se pudo generar id para ServicioPunto");
        }
        sp1.setIdServicioPunto(nextSpId);
        sp1.setServicio(servicio);
        sp1.setPunto(partida);
        sp1.setOrden(1L);

        servicioPuntoRepository.save(sp1);
        servicioPuntoRepository.flush();

        Long nextSpId2 = servicioPuntoRepository.nextId();
        if (nextSpId2 == null) {
            throw new IllegalStateException("No se pudo generar id para segundo ServicioPunto");
        }
        ServicioPunto sp2 = new ServicioPunto();
        sp2.setIdServicioPunto(nextSpId2);
        sp2.setServicio(servicio);
        sp2.setPunto(destino);
        sp2.setOrden(2L);

        servicioPuntoRepository.save(sp2);

        String msg = String.format("Cobro simulado: Cliente=%s Tarjeta=%d Valor=%.2f",
                clienteId, tarjetaNumero, valorServicio);
        System.out.println(msg);
        redirectAttributes.addFlashAttribute("success", "Servicio registrado y conductor asignado: " + conductor.getNombre()
                + ". " + msg);

        return "redirect:/servicios";
    }

    @GetMapping(value = "/tarjetas", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Map<String,Object>> tarjetasPorCliente(@RequestParam("clienteId") String clienteId) {
        List<TarjetaCredito> tarjetas = tarjetaCreditoRepository.findByUsuario_Cedula(clienteId);
        if (tarjetas == null || tarjetas.isEmpty()) {
            return Collections.emptyList();
        }

        return tarjetas.stream()
                .map(t -> {
                    Map<String,Object> m = new HashMap<>();
                    m.put("numero", t.getNumero());
                    m.put("nombre", t.getNombre());
                    return m;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/detail")
    public String servicioDetail(@PathVariable("id") Long idServicio, Model model) {
        Servicio s = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new IllegalStateException("Servicio no encontrado: " + idServicio));
        List<ServicioPunto> puntosServicio = servicioPuntoRepository.findByServicio_IdServicio(idServicio);
        puntosServicio.sort(Comparator.comparing(ServicioPunto::getOrden));

        model.addAttribute("servicio", s);
        model.addAttribute("vehicles", vehiculoRepository.findAll());
        model.addAttribute("puntos", puntoGeograficoRepository.findAll());
        model.addAttribute("puntosServicio", puntosServicio);
        return "servicioDetail";
    }

    //RF9: Registrar el final de un viaje para un usuario de servicios y un usuario conductor
    @PostMapping("/{id}/finish")
    @Transactional
    public String servicioFin(@PathVariable("id") Long idServicio,
                            @RequestParam(name="longitud", required=false) String longitud,
                            RedirectAttributes redirectAttributes) {

        Servicio s = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new IllegalStateException("Servicio no encontrado: " + idServicio));

        LocalDateTime now = LocalDateTime.now();
        s.setHoraFin(now);
        servicioRepository.save(s);

        if (s.getVehiculo() != null) {
            String placa = s.getVehiculo().getPlaca();
            LocalDate fecha = s.getHoraInicio() != null ? s.getHoraInicio().toLocalDate() : now.toLocalDate();
            Optional<Disponibilidad> maybeDisp = disponibilidadRepository
                    .findFirstByVehiculo_PlacaAndFechaAndHoraInicioLessThanEqualAndHoraFinGreaterThanEqualAndAsignada(
                            placa, fecha, s.getHoraInicio() != null ? s.getHoraInicio() : now, 
                            s.getHoraInicio() != null ? s.getHoraInicio() : now, 1);
            if (maybeDisp.isPresent()) {
                Disponibilidad disp = maybeDisp.get();
                disp.setAsignada(0); 
                disponibilidadRepository.save(disp);
            } else {
                System.out.println("No se encontró disponibilidad reservada para liberar (placa=" + placa + ")");
            }
        }

        redirectAttributes.addFlashAttribute("success", "Servicio " + idServicio + " finalizado a las " + now);
        return "redirect:/servicios/" + idServicio + "/finished";
    }

    @GetMapping("/{id}/finished")
    public String servicioFinishedView(@PathVariable("id") Long idServicio, Model model) {
        Servicio s = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new IllegalStateException("Servicio no encontrado: " + idServicio));
        model.addAttribute("servicio", s);
        return "servicioFin";
    }
}



