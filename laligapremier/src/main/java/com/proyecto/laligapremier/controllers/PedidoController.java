package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.Pedido;
import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;
import com.proyecto.laligapremier.service.ICarritoService;
import com.proyecto.laligapremier.service.IPedidoService;
import com.proyecto.laligapremier.service.IUsuarioService;
import com.proyecto.laligapremier.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class PedidoController {

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private ICarritoService carritoService;
    @Autowired
    private IPedidoService pedidoService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/pedidos")
    public String index(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Principal principal){

        Pageable pageRequest = PageRequest.of(page, 10);

        Page<Pedido> pedidos = pedidoService.findAll(pageRequest);

        PageRender<Pedido> pageRender = new PageRender<>("/pedidos", pedidos);

        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.addAttribute("userId", userId);
        }


        model.addAttribute("titulo" , "Camisetas disponibles");
        model.addAttribute("pedidos" , pedidos);
        model.addAttribute("page", pageRender);
        return "mostrar/pedidos";
    }

    @GetMapping(value = "/guardar-pedido")
    public String guardarPedido(Principal principal, RedirectAttributes flash) {
        Pedido pedido = new Pedido();

        pedido.setItems(carritoService.obtenerItemsDelCarrito());
        pedido.setUsuario(usuarioService.findByNombre(principal.getName()));
        pedido.setPrecioTotal(carritoService.calcularPrecioTotal());
        pedido.setNumCamisetas(carritoService.contadorItems());

        pedidoService.save(pedido);
        carritoService.reiniciarCarrito();
        flash.addFlashAttribute("success", "Pedido registrado correctamente.");
        return "redirect:/";
    }

}
