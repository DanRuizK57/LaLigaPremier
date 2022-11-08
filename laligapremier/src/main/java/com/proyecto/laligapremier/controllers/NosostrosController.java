package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * Clase controladora de la vista Nosotros.
 */
@Controller
public class NosostrosController {

    /**
     * Inyeccion de interfaces de la logica de servicios para usar la logica de la aplicacion.
     */

    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Metodo que envia los datos de los creadores de la aplicacion a la vista nosotros.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista de nosotros.
     */
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
