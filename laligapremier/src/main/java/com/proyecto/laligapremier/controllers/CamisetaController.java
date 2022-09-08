package com.proyecto.laligapremier.controllers;
import com.proyecto.laligapremier.service.ICamisetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("camiseta")
public class CamisetaController {
    @Autowired
    private ICamisetaService camisetaService;

    @RequestMapping(name = "/"  , method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("titulo" , "Listado de camisetas");
        model.addAttribute("camisetas" , camisetaService.findAll() );
        return "index";
    }

    @RequestMapping(name = "/selecciones" , method = RequestMethod.GET)
    public String selecciones(Model model){
        model.addAttribute("titulo" , "Listado de camisetas de selecciones");
        model.addAttribute("camisetasSelecciones" , camisetaService.findAll());
        return "selecciones";
    }

    public String equipos(Model model){
        model.addAttribute("titulo" , "Listaod de camisetas de equipos");
        model.addAttribute("camisetasEquipos" , camisetaService.findAll());
        return "equipos";
    }
}
