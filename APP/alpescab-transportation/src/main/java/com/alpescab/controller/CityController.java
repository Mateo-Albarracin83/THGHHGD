package com.alpescab.controller;

import com.alpescab.model.City;
import com.alpescab.repository.CityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CityController {

    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping("/cities")
    public String cities(Model model){
        List<City> cities = cityRepository.findAll();
        model.addAttribute("cities", cities);
        return "cities"; 
    }

    //RF1: Registrar una ciudad
    @GetMapping("/cities/new")
    public String cityForm(Model model) {
        model.addAttribute("city", new City());
        return "cityNew";
    }

    @PostMapping("cities/new/save")
    public String cityGuardar(@ModelAttribute City city) {
        cityRepository.save(city);
        return "redirect:/cities";
    }

    @GetMapping("cities/{id}/delete")
    public String cityEliminar(@PathVariable("id") int idCiudad){
        cityRepository.eliminarCiudad(idCiudad);
        return "redirect:/cities";
    }
}
