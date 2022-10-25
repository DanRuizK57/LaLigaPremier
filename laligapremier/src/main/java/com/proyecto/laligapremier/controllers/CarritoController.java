package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.exceptions.SinStockException;
import com.proyecto.laligapremier.models.dao.IItemPedidoDao;
import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.ItemPedido;
import com.proyecto.laligapremier.models.enums.Talla;
import com.proyecto.laligapremier.models.enums.TipoCamiseta;
import com.proyecto.laligapremier.service.ICamisetaService;
import com.proyecto.laligapremier.service.ICarritoService;
import com.proyecto.laligapremier.service.IItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@SessionAttributes("item")
public class CarritoController {

    @Autowired
    private ICarritoService carritoService;

    @Autowired
    private ICamisetaService camisetaService;

    @Autowired
    private IItemPedidoService itemPedidoService;

    @GetMapping("/carrito")
    public String shoppingCart(Model model) {

        model.addAttribute("items", carritoService.obtenerItemsDelCarrito());
        model.addAttribute("totalItems", carritoService.contadorItems().toString());
        model.addAttribute("totalPrecio", carritoService.calcularPrecioTotal().toString());
        return "mostrar/carrito";
    }

    @PostMapping("/ver-camiseta/{camisetaId}")
    public String addProductToCart(@Valid ItemPedido item,
                                   BindingResult result,
                                   @PathVariable("camisetaId") Long camisetaId,
                                   Model model,
                                   SessionStatus status) {

        Camiseta camiseta = camisetaService.findOne(camisetaId);

        item.setCamiseta(camiseta);

        if(result.hasErrors()){
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
    public String removeProductFromCart(@PathVariable("itemId") Long itemId, Model model) {

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

    // Cantidad de camisetas en revisión de implementación
    /*@GetMapping("/shoppingCart/checkout")
    public String checkout(Model model) {
        try {
            carritoService.checkout();
        } catch (SinStockException e) {
            model.addAttribute("outOfStockMessage", e.getMessage());
            return "mostrar/carrito";
        }
        return "redirect:/";
    }*/



}
