package com.alpescab.controller;

import com.alpescab.model.Disponibilidad;
import com.alpescab.model.Vehiculo;
import com.alpescab.repository.DisponibilidadRepository;
import com.alpescab.repository.VehiculoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/disponibilidades")
public class DisponibilidadController {
    
    private final DisponibilidadRepository disponibilidadRepository;
    private final VehiculoRepository vehiculoRepository;

    public DisponibilidadController(DisponibilidadRepository disponibilidadRepository, VehiculoRepository vehiculoRepository) {
        this.disponibilidadRepository = disponibilidadRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @GetMapping
    public String disponibilidades(Model model) {
        List<Disponibilidad> disponibilidad = disponibilidadRepository.findAll();
        model.addAttribute("disponibilidades", disponibilidad);
        return "disponibilidades";
    }

    //RF5 Registrar la disponibilidad de un usuario conductor y su vehículo para un servicio
    @GetMapping("/new")
    public String disponibilidadForm(Model model) {
        model.addAttribute("disponibilidad", new Disponibilidad());
        model.addAttribute("vehicles", vehiculoRepository.findAll());
        return "disponibilidadNew";
    }

    @PostMapping("/new/save")
    public String disponibilidadGuardar(@ModelAttribute Disponibilidad disponibilidad,
                                        @RequestParam("placa") String placa,
                                        Model model) {
        Optional<Vehiculo> optV = vehiculoRepository.findById(placa);
        if (!optV.isPresent()) {
            model.addAttribute("error", "Vehículo no encontrado.");
            model.addAttribute("vehicles", vehiculoRepository.findAll());
            return "disponibilidadNew";
        }
        Vehiculo v = optV.get();
        disponibilidad.setVehiculo(v);

        LocalDateTime inicio = disponibilidad.getHoraInicio();
        LocalDateTime fin = disponibilidad.getHoraFin();
        if (inicio == null || fin == null || !fin.isAfter(inicio)) {
            model.addAttribute("error", "Fechas/hora inválida: 'Hora fin' debe ser posterior a 'Hora inicio' y ambos deben estar llenos.");
            model.addAttribute("vehicles", vehiculoRepository.findAll());
            return "disponibilidadNew";
        }

        String cedulaConductor = v.getConductor() != null ? v.getConductor().getCedula() : null;
        if (cedulaConductor != null) {
            List<Disponibilidad> existentes = disponibilidadRepository.findByConductorCedula(cedulaConductor);
            for (Disponibilidad ex : existentes) {
                if (ex.getIdDisponibilidad() != null && ex.getIdDisponibilidad().equals(disponibilidad.getIdDisponibilidad())) {
                    continue;
                }
                LocalDateTime exInicio = ex.getHoraInicio();
                LocalDateTime exFin = ex.getHoraFin();
                if (exInicio == null || exFin == null) continue;

                boolean overlap = inicio.isBefore(exFin) && fin.isAfter(exInicio);
                if (overlap) {
                    model.addAttribute("error", "La disponibilidad se superpone con otra del mismo conductor (placa: " + ex.getVehiculo().getPlaca() + ").");
                    model.addAttribute("vehicles", vehiculoRepository.findAll());
                    return "disponibilidadNew";
                }
            }
        }
        disponibilidadRepository.save(disponibilidad);
        return "redirect:/disponibilidades";
    }

    //RF6 Modificar la disponibilidad de un vehículo para servicios
    @GetMapping("/{id}/edit")
    public String disponibilidadEditarForm(@PathVariable("id") Long idDisponibilidad, Model model) {
        Optional<Disponibilidad> disponibilidad = disponibilidadRepository.findById(idDisponibilidad);
        if (disponibilidad != null) {
            Disponibilidad d = disponibilidad.get();
            model.addAttribute("disponibilidad", d);
            model.addAttribute("vehicles", vehiculoRepository.findAll());
            return "disponibilidadEditar";
        } else {
            return "redirect:/disponibilidades";
        }
    }
    
    @PostMapping("/{id}/edit/save")
    public String disponibilidadEditarGuardar(
            @PathVariable("id") Long id,
            @RequestParam("placa") String placa,
            @ModelAttribute Disponibilidad formDisponibilidad,
            Model model) {

        Optional<Disponibilidad> opt = disponibilidadRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/disponibilidades";
        }
        Disponibilidad existing = opt.get();

        Optional<Vehiculo> optVeh = vehiculoRepository.findById(placa);
        if (optVeh.isEmpty()) {
            model.addAttribute("error", "Vehículo no encontrado.");
            model.addAttribute("disponibilidad", existing);
            model.addAttribute("vehicles", vehiculoRepository.findAll());
            return "disponibilidadEdit";
        }
        Vehiculo veh = optVeh.get();

        LocalDate fecha = formDisponibilidad.getFecha();
        LocalDateTime horaInicio = formDisponibilidad.getHoraInicio();
        LocalDateTime horaFin = formDisponibilidad.getHoraFin();
        if (horaInicio != null && horaFin != null && !horaInicio.isBefore(horaFin)) {
            model.addAttribute("error", "La hora de inicio debe ser anterior a la hora de fin.");
            model.addAttribute("disponibilidad", existing);
            model.addAttribute("vehicles", vehiculoRepository.findAll());
            return "disponibilidadEdit";
        }

        if (veh.getConductor() == null || veh.getConductor().getCedula() == null) {
            model.addAttribute("error", "El vehículo seleccionado no tiene conductor asociado.");
            model.addAttribute("disponibilidad", existing);
            model.addAttribute("vehicles", vehiculoRepository.findAll());
            return "disponibilidadEdit";
        }
        String cedulaConductor = veh.getConductor().getCedula();

        List<Disponibilidad> overlaps = disponibilidadRepository.findOverlapping(
            cedulaConductor,
            fecha,
            horaInicio,
            horaFin,
            id
        );

        if (!overlaps.isEmpty()) {
            model.addAttribute("error", "La disponibilidad se superpone con otra del mismo conductor.");
            existing.setFecha(formDisponibilidad.getFecha());
            existing.setHoraInicio(formDisponibilidad.getHoraInicio());
            existing.setHoraFin(formDisponibilidad.getHoraFin());
            existing.setTipoServicio(formDisponibilidad.getTipoServicio());
            model.addAttribute("disponibilidad", existing);
            model.addAttribute("vehicles", vehiculoRepository.findAll());
            return "disponibilidadEdit";
        }

        existing.setFecha(formDisponibilidad.getFecha());
        existing.setHoraInicio(formDisponibilidad.getHoraInicio());
        existing.setHoraFin(formDisponibilidad.getHoraFin());
        existing.setTipoServicio(formDisponibilidad.getTipoServicio());
        existing.setVehiculo(veh);

        disponibilidadRepository.save(existing);

        return "redirect:/disponibilidades";
    }

    @GetMapping("/{id}/delete")
    public String deleteDisponibilidad(@PathVariable("id") Long idDisponibilidad) {
        disponibilidadRepository.deleteById(idDisponibilidad);
        return "redirect:/disponibilidades";
    }
}
