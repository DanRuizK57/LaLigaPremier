package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.Filtro;
import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;
import com.proyecto.laligapremier.models.enums.TipoCamiseta;
import com.proyecto.laligapremier.models.enums.TipoPrecio;
import com.proyecto.laligapremier.service.ICamisetaService;
import com.proyecto.laligapremier.service.IUsuarioService;
import com.proyecto.laligapremier.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

@Controller
@SessionAttributes("camiseta")
public class FiltroController {

    @Autowired
    private ICamisetaService camisetaService;
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping(value = "/selecciones")
    public String ListarSelecciones(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Principal principal){
        Pageable pageRequest = PageRequest.of(page, 6);

        Page<Camiseta> camisetas = camisetaService.listarPorTipo(TipoCamiseta.SELECCION, pageRequest);

        PageRender<Camiseta> pageRender = new PageRender<>("/selecciones", camisetas);

        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.addAttribute("userId", userId);
        }

        model.addAttribute("titulo" , "Listado de camisetas de selecciones");
        model.addAttribute("camisetas" , camisetas);
        model.addAttribute("page", pageRender);
        model.addAttribute("marcas" , Marca.values());
        model.addAttribute("tallas" , Talla.values());
        model.addAttribute("precios", TipoPrecio.values());
        model.addAttribute("objetoFiltro" , new Filtro());
        return "mostrar/selecciones";
    }

    @GetMapping(value = "/equipos")
    public String ListarEquipos(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Principal principal){
        Pageable pageRequest = PageRequest.of(page, 6);

        Page<Camiseta> camisetas = camisetaService.listarPorTipo(TipoCamiseta.EQUIPO, pageRequest);

        PageRender<Camiseta> pageRender = new PageRender<>("/equipos", camisetas);

        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.addAttribute("userId", userId);
        }

        model.addAttribute("titulo" , "Listado de camisetas de equipos");
        model.addAttribute("camisetas" , camisetas);
        model.addAttribute("page", pageRender);
        model.addAttribute("marcas" , Marca.values());
        model.addAttribute("tallas" , Talla.values());
        model.addAttribute("precios", TipoPrecio.values());
        model.addAttribute("objetoFiltro" , new Filtro());
        return "mostrar/equipos";
    }

    @GetMapping("/busqueda")
    public String buscarCamisetas(@RequestParam(name = "page", defaultValue = "0") int page,
                                  Model model,
                                  @RequestParam(value = "query", required = false) String q,
                                  Principal principal) {
        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.addAttribute("userId", userId);
        }

        Pageable pageRequest = PageRequest.of(page, 6);

        Page<Camiseta> camisetas = camisetaService.findByNombre(q, pageRequest);

        PageRender<Camiseta> pageRender = new PageRender<>("/busqueda", camisetas);

        model.addAttribute("camisetas", camisetas);
        model.addAttribute("titulo", "Resultados de búsqueda:");
        model.addAttribute("page", pageRender);
        model.addAttribute("numCamisetasEncontradas", camisetas.getTotalElements());
        model.addAttribute("marcas" , Marca.values());
        model.addAttribute("tallas" , Talla.values());
        model.addAttribute("precios", TipoPrecio.values());
        model.addAttribute("objetoFiltro" , new Filtro());
        return "mostrar/busqueda";
    }

}
