package com.alpescab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.alpescab.model.Usuario;
import com.alpescab.model.UsuarioCliente;
import com.alpescab.model.UsuarioConductor;
import com.alpescab.model.Vehiculo;
import com.alpescab.model.Disponibilidad;
import com.alpescab.repository.UsuarioRepository;

import java.util.List;
@Controller
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;


    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("users", usuarios);
        return "users";
    }

    //RF2: Registrar un usuario de servicio
    //RF3: Registrar un usuario conductor
    @GetMapping("/users/new")
    public String usersForm(Model model) {
        model.addAttribute("users", new Usuario());
        return "userNew";
    }

    @PostMapping("/users/new/save")
    public String usersGuardar(@ModelAttribute Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteVehicle(@PathVariable("id") String cedula) {
        usuarioRepository.deleteById(cedula);
        return "redirect:/users";
    }
}
