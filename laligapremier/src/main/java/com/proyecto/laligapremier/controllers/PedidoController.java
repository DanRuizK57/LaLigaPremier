package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.Item;
import com.proyecto.laligapremier.models.entity.Pedido;
import com.proyecto.laligapremier.service.ICarritoService;
import com.proyecto.laligapremier.service.IItemService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.UUID;

@Controller
public class PedidoController {

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private ICarritoService carritoService;
    @Autowired
    private IPedidoService pedidoService;

    @Autowired
    private IItemService itemService;

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

        pedido.setCodigo(UUID.randomUUID().toString());

        carritoService.obtenerItemsDelCarrito().stream().forEach(
                p -> {
                    Item item = new Item();
                    item.setNombre(p.getCamiseta().getNombre());
                    item.setDescripcion(p.getCamiseta().getDescripcion());
                    item.setNombreJugador(p.getCamiseta().getJugador());
                    item.setTalla(p.getCamiseta().getTalla());
                    item.setCantidad(p.getCantidad());
                    item.setCodigo(pedido.getCodigo());
                    itemService.save(item);
                }
        );

        pedido.setUsuario(usuarioService.findByNombre(principal.getName()));
        pedido.setPrecioTotal(carritoService.calcularPrecioTotal());
        pedido.setNumCamisetas(carritoService.contadorItems());

        pedidoService.save(pedido);
        carritoService.reiniciarCarrito();
        flash.addFlashAttribute("success", "Pedido registrado correctamente.");
        return "redirect:/";
    }

    @GetMapping("ver-pedido/{id}")
    public String mostrarPedido(@PathVariable(value = "id")Long id, Map<String, Object> model){
        Pedido pedido = pedidoService.findOne(id);
        List<Item> items  = itemService.listar().stream()
                .filter(p -> p.getCodigo()
                        .equals(pedido.getCodigo()))
                .toList();
        model.put("cantidad" , "cantidad de camisetas pedidas:  " + pedido.getNumCamisetas());
        model.put("precio" , "precio total del pedido: " + pedido.getPrecioTotal());
        model.put("titulo" , "items del pedido N° "  + pedido.getId());
        model.put("items" , items );
        return "mostrar/mostrar_pedidos";
    }

}
