package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.Filtro;
import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ICamisetaService camisetaService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping(value = {"/index", "/"})
    public String index(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Principal principal){

        Filtro filtro = new Filtro();



        Pageable pageRequest = PageRequest.of(page, 6); // 6 elementos por página

        Page<Camiseta> camisetas = camisetaService.findAll(pageRequest); // Lista paginada

        PageRender<Camiseta> pageRender = new PageRender<>("/", camisetas);

        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.addAttribute("userId", userId);
        }


        model.addAttribute("objetoFiltro" , filtro);
        model.addAttribute("titulo" , "Camisetas disponibles");
        model.addAttribute("camisetas" , camisetas);
        model.addAttribute("page", pageRender);
        model.addAttribute("marcas" , Marca.values());
        model.addAttribute("tallas" , Talla.values());
        model.addAttribute("precios", TipoPrecio.values());


        return "mostrar/index";
    }
    @PostMapping(name="/filtro")
    public String ejecutarFiltro(@Valid @ModelAttribute("objetoFiltro") Filtro objetoFiltro,
            BindingResult result
            , Model model){

        List<Camiseta> camisetasFiltradas =  camisetaService.findAll().stream()
                .filter(p -> p.getTalla().getTalla()
                        .equals(objetoFiltro.getMarca())
                        &&
                        p.getMarca().getNombre()
                                .equals(objetoFiltro.getMarca())
                        &&      p.getPrecio() <= Integer.parseInt(objetoFiltro.getPrecio().getPrecio())
                )
                .toList();
        System.out.println("***************************** LLEGO HASTA AQI *****************************");
        model.addAttribute("camisetas" ,camisetasFiltradas);
        model.addAttribute("titulo" , "Camisetas disponibles");
        model.addAttribute("marcas" , Marca.values());
        model.addAttribute("tallas" , Talla.values());
        model.addAttribute("precios", TipoPrecio.values());
        return "mostrar/index";
    }
}
