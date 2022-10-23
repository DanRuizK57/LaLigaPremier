package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.exceptions.SinStockException;
import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.ItemPedido;
import com.proyecto.laligapremier.service.ICamisetaService;
import com.proyecto.laligapremier.service.ICarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping()
@SessionAttributes("pedido")
public class CarritoController {

    @Autowired
    private ICarritoService carritoService;

    @Autowired
    private ICamisetaService camisetaService;

    @GetMapping("/shoppingCart")
    public String shoppingCart(Model model) {
        model.addAttribute("camisetas", carritoService.getProductsInCart());
        model.addAttribute("total", carritoService.getTotal().toString());
        return "mostrar/carrito";
    }

    @GetMapping("/shoppingCart/addProduct/{camisetaId}")
    public String addProductToCart(@PathVariable("camisetaId") Long camisetaId) {



        Camiseta camiseta = camisetaService.findById(camisetaId).orElse(null);


        if (camiseta != null) {
            carritoService.addProduct(camiseta);
            return "redirect:/shoppingCart";
        }
        return "redirect:/";
    }

    @GetMapping("/shoppingCart/removeProduct/{camisetaId}")
    public String removeProductFromCart(@PathVariable("camisetaId") Long camisetaId) {

        Camiseta camiseta = camisetaService.findById(camisetaId).orElse(null);

        if (camiseta != null) {
            carritoService.removeProduct(camiseta);
        }
        return "mostrar/carrito";
    }

    // Cantidad de camisetas en revisión de implementación
    @GetMapping("/shoppingCart/checkout")
    public String checkout(Model model) {
        try {
            carritoService.checkout();
        } catch (SinStockException e) {
            model.addAttribute("outOfStockMessage", e.getMessage());
            return "mostrar/carrito";
        }
        return "redirect:/";
    }



}
