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

import javax.validation.Valid;
import java.security.Principal;

/**
 * Clase controladora del carrito de la aplicacion. Muestra, agrega, elimina, suma y resta elementos del carrito
 * para mostrarlos en la vista
 */

@Controller
@SessionAttributes("item")
public class CarritoController {
    /**
     * Inyeccion de interfaces de la logica de servicios para usar la logica de la aplicacion.
     */

    @Autowired
    private ICarritoService carritoService;
    @Autowired
    private ICamisetaService camisetaService;
    @Autowired
    private IItemPedidoService itemPedidoService;
    @Autowired
    private IUsuarioService usuarioService;

    /**
     *Metodo que muestra la instancia creada del carrito con los items, el total de items y el precio total.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista del carrito
     */
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

    /**
     * Metodo que agrega una camiseta al carrito de compras.
     * @param item parametro de tipo ItemPedido, usado para ser agregado al carrito.
     * @param result parametro de tipo BindingResult, usado para validar el objeto y contener errores que pueden producirse
     * @param camisetaId parametro de tipo Long, usado para obtener una camiseta desde la base de datos
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param status parametro de tipo SessionStatusm usado para indicar que el estado de la sesion esta completa
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista principal de la aplicacion.
     */
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

    /**
     * Metodo que elimina una camiseta del carrito de compras
     * @param itemId parametro de tipo Long, usado para encontrar el item solicitado
     * @return vista principal de la aplicacion
     */
    @GetMapping("/eliminarCamiseta/{itemId}")
    public String eliminarCamiseta(@PathVariable("itemId") Long itemId) {

        ItemPedido item = itemPedidoService.findOne(itemId);

        if (item != null) {
            carritoService.eliminarItem(itemId);
            itemPedidoService.delete(itemId);
            return "redirect:/carrito";
        }
        return "redirect:/";
    }

    /**
     * Metodo que suma en 1 la cantidad de un producto seleccionado
     * @param itemId pamaretro de tipo Long, usado para sumar la cantida
     * @param itemId pamaretro de tipo Long, usado para sumar la cantida del item solicitado
     * @return
     */

    @GetMapping("/sumar/{itemId}")
    public String sumar(@PathVariable("itemId") Long itemId) {
        carritoService.sumarCantidad(itemId);
        return "redirect:/carrito";
    }

    /**
     * Metodo que resta en 1 la cantidad de un producto seleccionado.
     * @param itemId pamaretro de tipo Long, usado para restar la cantida
     * @param itemId pamaretro de tipo Long, usado para restar la cantida del item solicitado
     * @return
     */

    @GetMapping("/restar/{itemId}")
    public String restar(@PathVariable("itemId") Long itemId) {
        carritoService.restarCantidad(itemId);
        return "redirect:/carrito";
    }
}
