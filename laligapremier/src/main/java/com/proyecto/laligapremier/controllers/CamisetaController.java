package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.service.ICamisetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@SessionAttributes("camiseta")
public class CamisetaController {
    @Autowired
    private ICamisetaService camisetaService;

    @GetMapping(value = "/selecciones")
    public String ListarSelecciones(Model model){
        model.addAttribute("titulo" , "Listado de camisetas de selecciones");
        model.addAttribute("camisetasSelecciones" , camisetaService.findAll());
        return "mostrar/selecciones";
    }

    @GetMapping(value = "/equipos")
    public String ListarEquipos(Model model){
        model.addAttribute("titulo" , "Listado de camisetas de equipos");
        model.addAttribute("camisetasEquipos" , camisetaService.findAll());
        return "mostrar/equipos";
    }

    @GetMapping(value = "/ver-camiseta")
    public String verCamiseta(@PathVariable(value = "id")Long id, Map <String, Object> model , RedirectAttributes flash){
        Camiseta camiseta = camisetaService.findOne(id);
        if(camiseta == null){
            flash.addAttribute("error" , "Camiseta no encontrada");
            return "redirect:/mostrar/equipos";
        }
        model.put("camiseta", camiseta);
        model.put("titulo", "detalle de la camiseta ");
        return "camiseta/ver_camiseta";
    }

    @GetMapping(value = "/formulario")
    public String formulario(Map<String , Object>model){

        Camiseta camiseta = new Camiseta(); 
        model.put("camiseta" , camiseta); 
        return "camiseta/form_camiseta"; 
    
    }

    @GetMapping(value = "/filtros")
    public String filtros(Model model){
        return "filtros";
    }

}
