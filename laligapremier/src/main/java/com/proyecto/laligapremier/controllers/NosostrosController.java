package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class NosostrosController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping(value = "/nosotros")
    public String nosotros(Model model, Principal principal) {

        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.addAttribute("userId", userId);
        }

        model.addAttribute("titulo" , "Informacion sobre los creadores");
        return "mostrar/nosotros";
    }
}
