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
     * Inyección de interfaz de la lógica de servicios para usar la lógica de la aplicación.
     */

    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Método que envía los datos de los creadores de la aplicación a la vista nosotros.
     * @param model parámetro de tipo Model, usado para recibir o entregar parámetros desde una vista.
     * @param principal parámetro de tipo Principal, usado para obtener al usuario en la sesión activa.
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
