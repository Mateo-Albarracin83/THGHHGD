package com.alpescab.controller;

import com.alpescab.model.Vehiculo;
import com.alpescab.model.Usuario;
import com.alpescab.model.City;
import com.alpescab.repository.VehiculoRepository;
import com.alpescab.repository.UsuarioRepository;
import com.alpescab.repository.CityRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vehicles")
public class VehiculoController {

    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CityRepository cityRepository;

    public VehiculoController(VehiculoRepository vehiculoRepository, UsuarioRepository usuarioRepository, CityRepository cityRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cityRepository = cityRepository;
    }

    @GetMapping
    public String vehicles(Model model) {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        model.addAttribute("vehicles", vehiculos);
        return "vehicles";
    }

    //RF4: Registrar un vehículo para un usuario conductor
    @GetMapping("/new")
    public String vehicleForm(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());
        model.addAttribute("conductores", usuarioRepository.findAll());
        model.addAttribute("cities", cityRepository.findAll());
        return "vehicleNew";
    }

    @PostMapping("/new/save")
    public String vehiclesGuardar(@ModelAttribute Vehiculo vehiculo,
                                  @RequestParam("conductorCedula") String conductorCedula,
                                  @RequestParam(value = "ciudadId", required = false) Long ciudadId,
                                  Model model) {
        Optional<Usuario> maybeConductor = usuarioRepository.findById(conductorCedula);
        if (maybeConductor.isEmpty()) {
            model.addAttribute("error", "Conductor no encontrado: " + conductorCedula);
            model.addAttribute("vehiculo", vehiculo);
            model.addAttribute("conductores", usuarioRepository.findAll());
            model.addAttribute("cities", cityRepository.findAll());
            return "vehicleNew";
        }
        vehiculo.setConductor(maybeConductor.get());

        if (ciudadId != null) {
            cityRepository.findById(ciudadId).ifPresent(vehiculo::setCiudadExpedicion);
        }

        vehiculoRepository.save(vehiculo);
        return "redirect:/vehicles";
    }

    @GetMapping("/{id}/delete")
    public String deleteVehicle(@PathVariable("id") String placa) {
        vehiculoRepository.deleteById(placa);
        return "redirect:/vehicles";
    }
}

