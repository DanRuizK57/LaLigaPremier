package com.proyecto.laligapremier.controllers;
import com.proyecto.laligapremier.service.ICamisetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("camiseta")
public class CamisetaController {
    @Autowired
    private ICamisetaService camisetaService;

    @GetMapping(value = "/selecciones")
    public String selecciones(Model model){
        model.addAttribute("titulo" , "Listado de camisetas de selecciones");
        model.addAttribute("camisetasSelecciones" , camisetaService.findAll());
        return "mostrar/selecciones";
    }

    @GetMapping(value = "/equipos")
    public String equipos(Model model){
        model.addAttribute("titulo" , "Listado de camisetas de equipos");
        model.addAttribute("camisetasEquipos" , camisetaService.findAll());
        return "mostrar/equipos";
    }

    @GetMapping(value = "/ver-camiseta")
    public String verCamiseta(Model model){
        return "camiseta/ver_camiseta";
    }

    @GetMapping(value = "/formulario")
    public String formulario(Model model){
        return "camiseta/form_camiseta";
    }

}
