package com.proyecto.laligapremier.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NosostrosController {
    @GetMapping(value = "/nosotros")
    public String nosotros(Model model){
        model.addAttribute("titulo" , "Informacion sobre los creadores");
        return "nosotros";
    }
}
