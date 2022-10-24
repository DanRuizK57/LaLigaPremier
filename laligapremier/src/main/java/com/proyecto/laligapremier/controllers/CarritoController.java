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

import javax.validation.Valid;

@Controller
@SessionAttributes("item")
public class CarritoController {

    @Autowired
    private ICarritoService carritoService;

    @Autowired
    private ICamisetaService camisetaService;

    @Autowired
    private IItemPedidoService itemPedidoService;

    @GetMapping("/shoppingCart")
    public String shoppingCart(Model model) {

        model.addAttribute("items", carritoService.getProductsInCart());
        model.addAttribute("total", carritoService.getTotal().toString());
        return "mostrar/carrito";
    }

    @PostMapping("/shoppingCart/addProduct/{camisetaId}")
    public String addProductToCart(@Valid ItemPedido item,
                                   @PathVariable("camisetaId") Long camisetaId,
                                   BindingResult result,
                                   Model model,
                                   SessionStatus status) {

        Camiseta camiseta = camisetaService.findOne(camisetaId);

        item.setCamiseta(camiseta);

        if(result.hasErrors()){
            model.addAttribute("tipoCamisetas", TipoCamiseta.values());
            model.addAttribute("tallas", Talla.values());
            return "camiseta/form_camiseta";
        }

        if (camiseta != null) {
            carritoService.addProduct(item);
            itemPedidoService.save(item);
            status.setComplete();
            return "redirect:/shoppingCart";
        }
        return "redirect:/";
    }

    @GetMapping("/shoppingCart/removeProduct/{itemId}")
    public String removeProductFromCart(@PathVariable("itemId") Long itemId, Model model) {

        ItemPedido item = itemPedidoService.findOne(itemId);

        if (item != null) {
            carritoService.removeProduct(item);
            itemPedidoService.delete(itemId);
            return "redirect:/shoppingCart";
        }
        return "redirect:/";
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
