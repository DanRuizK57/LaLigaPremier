package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;
import com.proyecto.laligapremier.service.ICamisetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    private ICamisetaService camisetaService;

    @GetMapping(value = {"/index", "/"})
    public String index(Model model){
        model.addAttribute("titulo" , "Camisetas disponibles");
        model.addAttribute("camisetas" , camisetaService.findAll());
        model.addAttribute("marcas" , Marca.values());
        model.addAttribute("tallas" , Talla.values());
        return "mostrar/index";
    }

    @GetMapping(value = "/index-admin")
    public String indexAdmin(Model model){
        model.addAttribute("titulo" , "Camisetas disponibles");
        model.addAttribute("camisetas" , camisetaService.findAll());
        return "mostrar/index_admin";
    }

    @GetMapping(value = "/index-usuario")
    public String indexUsuario(Model model){
        model.addAttribute("titulo" , "Camisetas disponibles");
        model.addAttribute("camisetas" , camisetaService.findAll());
        return "mostrar/index_usuario";
    }
}
