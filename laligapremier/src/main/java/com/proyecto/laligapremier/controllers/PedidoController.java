package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.models.entity.Pedido;
import com.proyecto.laligapremier.models.entity.Usuario;
import com.proyecto.laligapremier.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pedido")
@SessionAttributes("pedido")
public class PedidoController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/form/{usuarioId}")
    public String crear(@PathVariable(name = "usuarioId") Long usuarioId,
                        Model model, RedirectAttributes flash) {

        Usuario usuario = usuarioService.findOne(usuarioId);

        if (usuario == null) {
            flash.addFlashAttribute("error", "¡El usuario no existe en la BBDD!");
            return "redirect:/";
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);

        model.addAttribute("pedido", pedido);
        model.addAttribute("titulo", "Añadir al carrito");

        return "mostrar/carrito";
    }

}
