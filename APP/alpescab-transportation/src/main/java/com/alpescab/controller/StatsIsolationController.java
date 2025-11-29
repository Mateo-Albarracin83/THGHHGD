package com.alpescab.controller;

import com.alpescab.model.Servicio;
import com.alpescab.repository.ServicioRepository;
import com.alpescab.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/stats/rfc1/isolation")
public class StatsIsolationController {

    private final ServicioRepository servicioRepository;
    private final UsuarioRepository usuarioRepository;

    public StatsIsolationController(ServicioRepository servicioRepository,
                                    UsuarioRepository usuarioRepository) {
        this.servicioRepository = servicioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String form(Model model) {
        model.addAttribute("users", usuarioRepository.findAll());
        return "rfc1_isolation_form";
    }

    @PostMapping("/serializable")
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public String runSerializable(@RequestParam("clienteId") String clienteId, Model model) throws InterruptedException {
        LocalDateTime start = LocalDateTime.now();
        List<Servicio> before = servicioRepository.findByCliente_CedulaOrderByHoraInicioDesc(clienteId);

        Thread.sleep(30_000);

        List<Servicio> after = servicioRepository.findByCliente_CedulaOrderByHoraInicioDesc(clienteId);
        LocalDateTime end = LocalDateTime.now();

        model.addAttribute("nivel", "SERIALIZABLE");
        model.addAttribute("clienteId", clienteId);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("before", before);
        model.addAttribute("after", after);
        return "rfc1_isolation_result";
    }

    @PostMapping("/read_committed")
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public String runReadCommitted(@RequestParam("clienteId") String clienteId, Model model) throws InterruptedException {
        LocalDateTime start = LocalDateTime.now();
        List<Servicio> before = servicioRepository.findByCliente_CedulaOrderByHoraInicioDesc(clienteId);

        Thread.sleep(30_000);

        List<Servicio> after = servicioRepository.findByCliente_CedulaOrderByHoraInicioDesc(clienteId);
        LocalDateTime end = LocalDateTime.now();

        model.addAttribute("nivel", "READ_COMMITTED");
        model.addAttribute("clienteId", clienteId);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("before", before);
        model.addAttribute("after", after);
        return "rfc1_isolation_result";
    }
}
