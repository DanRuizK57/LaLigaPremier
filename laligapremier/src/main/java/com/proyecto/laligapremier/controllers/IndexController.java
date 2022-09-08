package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.service.ICamisetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    private ICamisetaService camisetaService;

    @GetMapping(value = "/index")
    public String index(Model model){
        model.addAttribute("titulo" , "Camisetas disponibles");
        model.addAttribute("camisetas" , camisetaService.findAll());
        return "index";
    }
}
