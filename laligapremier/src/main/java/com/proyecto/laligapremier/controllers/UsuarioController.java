package com.proyecto.laligapremier.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @GetMapping(value="/iniciar-sesion")
    public String iniciarSesion(Model model) {
        model.addAttribute("titulo" , "Iniciar Sesión");
        return "inicio_sesion";
    }

    @GetMapping(value="/registro")
    public String registro(Model model) {
        model.addAttribute("titulo" , "Registrar Cuenta");
        return "registro";
    }

    @GetMapping(value="/editar-perfil")
    public String editarPerfil(Model model) {
        model.addAttribute("titulo" , "Editar Perfil");
        return "editar_perfil";
    }

}
