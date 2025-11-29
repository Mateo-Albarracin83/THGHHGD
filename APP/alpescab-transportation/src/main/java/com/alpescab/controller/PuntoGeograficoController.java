package com.alpescab.controller;

import com.alpescab.model.PuntoGeografico;
import com.alpescab.repository.PuntoGeograficoRepository;
import com.alpescab.model.City;
import com.alpescab.repository.CityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/puntos")
public class PuntoGeograficoController {
    private final PuntoGeograficoRepository puntoGeograficoRepository;
    private final CityRepository cityRepository;
    
    public PuntoGeograficoController(PuntoGeograficoRepository puntoGeograficoRepository, CityRepository cityRepository) {
        this.cityRepository = cityRepository;
        this.puntoGeograficoRepository = puntoGeograficoRepository;
    }

    @GetMapping
    public String puntos(Model model) {
        List<PuntoGeografico> puntos = puntoGeograficoRepository.findAll();
        model.addAttribute("puntos", puntos);
        return "puntos";
    }

    //RF7 Registrar un punto geografico
    @GetMapping("/new")
    public String puntosForm(Model model) {
        model.addAttribute("puntos", new PuntoGeografico());
        model.addAttribute("cities", cityRepository.findAll());
        return "puntosNew";
    }

    @PostMapping("/new/save")
    public String guardarPunto(@ModelAttribute("punto") PuntoGeografico punto, @RequestParam("ciudadId") Long ciudadId,Model model) {
        Optional<City> maybeCity = cityRepository.findById(ciudadId);
        if (maybeCity.isEmpty()) {
            model.addAttribute("error", "Ciudad no encontrada: " + ciudadId);
            model.addAttribute("cities", cityRepository.findAll());
            return "puntosNew";
        }
        punto.setCiudad(maybeCity.get());
        puntoGeograficoRepository.save(punto);

        return "redirect:/puntos";
    }

    @GetMapping("/{id}/delete")
    public String deletePunto(@PathVariable("id") Long id) {
        puntoGeograficoRepository.deleteById(id);
        return "redirect:/puntos";
    }
}
