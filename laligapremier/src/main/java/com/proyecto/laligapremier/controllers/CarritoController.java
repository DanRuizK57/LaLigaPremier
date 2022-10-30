package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.ItemPedido;
import com.proyecto.laligapremier.models.enums.Talla;
import com.proyecto.laligapremier.service.ICamisetaService;
import com.proyecto.laligapremier.service.ICarritoService;
import com.proyecto.laligapremier.service.IItemPedidoService;
import com.proyecto.laligapremier.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@SessionAttributes("item")
public class CarritoController {

    @Autowired
    private ICarritoService carritoService;
    @Autowired
    private ICamisetaService camisetaService;
    @Autowired
    private IItemPedidoService itemPedidoService;
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/carrito")
    public String mostrarCarrito(Model model, Principal principal) {

        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.addAttribute("userId", userId);
        }

        model.addAttribute("items", carritoService.obtenerItemsDelCarrito());
        model.addAttribute("totalItems", carritoService.contadorItems().toString());
        model.addAttribute("totalPrecio", carritoService.calcularPrecioTotal().toString());
        return "mostrar/carrito";
    }

    @PostMapping("/agregar-camiseta/{camisetaId}")
    public String agregarCamiseta(@Valid ItemPedido item,
                                   BindingResult result,
                                   @PathVariable("camisetaId") Long camisetaId,
                                   Model model,
                                   SessionStatus status,
                                   Principal principal) {

        Camiseta camiseta = camisetaService.findOne(camisetaId);
        item.setCamiseta(camiseta);

        if(result.hasErrors()){
            if (principal != null) {
                int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
                model.addAttribute("userId", userId);
            }

            model.addAttribute("error" , "Completa la cantidad y la talla para continuar.");
            model.addAttribute("tallas", Talla.values());
            model.addAttribute("camiseta", camiseta);
            model.addAttribute("item", item);
            return "camiseta/ver_camiseta";
        }

        if (camiseta != null) {
            carritoService.añadirItem(item);
            itemPedidoService.save(item);
            status.setComplete();
            return "redirect:/carrito";
        }
        return "redirect:/";
    }

    @GetMapping("/eliminarCamiseta/{itemId}")
    public String eliminarCamiseta(@PathVariable("itemId") Long itemId, Model model) {

        ItemPedido item = itemPedidoService.findOne(itemId);

        if (item != null) {
            carritoService.eliminarItem(itemId);
            itemPedidoService.delete(itemId);
            return "redirect:/carrito";
        }
        return "redirect:/";
    }

    @GetMapping("/sumar/{itemId}")
    public String sumar(@PathVariable("itemId") Long itemId, HttpSession session) {
        carritoService.sumarCantidad(itemId);
        return "redirect:/carrito";
    }

    @GetMapping("/restar/{itemId}")
    public String restar(@PathVariable("itemId") Long itemId, HttpSession session) {
        carritoService.restarCantidad(itemId);
        return "redirect:/carrito";
    }



}
