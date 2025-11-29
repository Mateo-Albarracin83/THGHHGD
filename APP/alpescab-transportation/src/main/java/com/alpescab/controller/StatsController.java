package com.alpescab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.alpescab.repository.UsuarioRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class StatsController {

    private final JdbcTemplate jdbc;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public StatsController(JdbcTemplate jdbc, UsuarioRepository usuarioRepository) {
        this.jdbc = jdbc;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/stats")
    public String statsHome() {
        return "stats";
    }

    // RFC1: histórico de servicios de un usuario (se muestra formulario si no viene clienteId)
    @GetMapping("/stats/rfc1")
    public String rfc1Form(@RequestParam(value = "clienteId", required = false) String clienteId, Model model) {
        model.addAttribute("users", usuarioRepository.findAll());
        if (clienteId != null && !clienteId.isBlank()) {
            String sql = 
                "SELECT s.idServicio, s.tipo, s.distancia, s.tarifa, s.horaInicio, s.horaFin, " +
                "s.conductorCedula, s.placa, tp.tarifaPasajeros, tme.tarifaMercancias, ed.tarifaDomicilio " +
                "FROM Servicio s " +
                "LEFT JOIN TransportePasajeros tp ON tp.idServicio = s.idServicio " +
                "LEFT JOIN TransporteMercancias tme ON tme.idServicio = s.idServicio " +
                "LEFT JOIN EntregaDomicilio ed ON ed.idServicio = s.idServicio " +
                "WHERE s.clienteCedula = ? " +
                "ORDER BY s.horaInicio DESC";
            List<Map<String,Object>> rows = jdbc.queryForList(sql, clienteId);
            model.addAttribute("rows", rows);
            model.addAttribute("clienteSel", clienteId);
        }
        return "rfc1";
    }

    // RFC2: top 20 conductores por cantidad de servicios
    @GetMapping("/stats/rfc2")
    public String rfc2(Model model) {
        String sql =
            "SELECT u.cedula, u.nombre, COUNT(*) AS servicios_prestados " +
            "FROM Servicio s " +
            "JOIN Usuario u ON u.cedula = s.conductorCedula " +
            "GROUP BY u.cedula, u.nombre " +
            "ORDER BY servicios_prestados DESC " +
            "FETCH FIRST 20 ROWS ONLY";
        List<Map<String,Object>> rows = jdbc.queryForList(sql);
        model.addAttribute("rows", rows);
        return "rfc2";
    }

    // RFC3: dinero obtenido por conductores por vehículo (agregado por placa)
    @GetMapping("/stats/rfc3")
    public String rfc3(Model model) {
        String sql =
            "SELECT s.conductorCedula, s.placa, COUNT(*) AS servicios_count, SUM(NVL(s.valorServicio,0)) AS brutoRecibido " +
            "FROM Servicio s " +
            "WHERE s.conductorCedula IS NOT NULL " +
            "GROUP BY s.conductorCedula, s.placa " +
            "ORDER BY brutoRecibido DESC";
        List<Map<String,Object>> rows = jdbc.queryForList(sql);
        model.addAttribute("rows", rows);
        return "rfc3";
    }

    // RFC4: utilización en una ciudad en un rango de fechas
    @GetMapping("/stats/rfc4")
    public String rfc4(
            @RequestParam(value = "ciudad", required = false) String ciudad,
            @RequestParam(value = "desde", required = false) String desde,
            @RequestParam(value = "hasta", required = false) String hasta,
            Model model) {

        if (ciudad == null || desde == null || hasta == null) {
            model.addAttribute("ciudad", ciudad);
            model.addAttribute("desde", desde);
            model.addAttribute("hasta", hasta);
            return "rfc4";
        }

        Date dDesde = Date.valueOf(LocalDate.parse(desde));
        Date dHasta = Date.valueOf(LocalDate.parse(hasta));

        String sql =
            "WITH servicios_ciudad AS ( " +
            "  SELECT DISTINCT s.idServicio, s.tipo, tp.nivelTransporte " +
            "  FROM Servicio s " +
            "  JOIN ServicioPunto sp ON sp.idServicio = s.idServicio " +
            "  JOIN PuntoGeografico pg ON pg.idPunto = sp.idPunto " +
            "  LEFT JOIN TransportePasajeros tp ON tp.idServicio = s.idServicio " +
            "  WHERE pg.ciudad = ? AND TRUNC(s.horaInicio) BETWEEN ? AND ? " +
            ") " +
            "SELECT tipo, NVL(nivelTransporte,'N/A') AS nivelTransporte, COUNT(*) AS cantidad_servicios, " +
            "ROUND( COUNT(*) * 100.0 / SUM(COUNT(*)) OVER (), 2 ) AS porcentaje_del_total " +
            "FROM servicios_ciudad " +
            "GROUP BY tipo, nivelTransporte " +
            "ORDER BY cantidad_servicios DESC";

        List<Map<String,Object>> rows = jdbc.queryForList(sql, ciudad, dDesde, dHasta);
        model.addAttribute("rows", rows);
        model.addAttribute("ciudadSel", ciudad);
        model.addAttribute("desdeSel", desde);
        model.addAttribute("hastaSel", hasta);
        return "rfc4";
    }
}
