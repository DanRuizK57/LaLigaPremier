package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.Usuario;
import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;
import com.proyecto.laligapremier.service.ICamisetaService;
import com.proyecto.laligapremier.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class IndexController {
    @Autowired
    private ICamisetaService camisetaService;

    @GetMapping(value = {"/index", "/"})
    public String index(@RequestParam(name = "page", defaultValue = "0") int page, Model model, SessionStatus status){

        Pageable pageRequest = PageRequest.of(page, 6); // 6 elementos por página

        Page<Camiseta> camisetas = camisetaService.findAll(pageRequest); // Lista paginada

        PageRender<Camiseta> pageRender = new PageRender<>("/", camisetas);

        Usuario usuario = null;

        model.addAttribute("titulo" , "Camisetas disponibles");
        model.addAttribute("camisetas" , camisetas);
        model.addAttribute("page", pageRender);
        model.addAttribute("marcas" , Marca.values());
        model.addAttribute("tallas" , Talla.values());
        model.addAttribute("usuario", usuario);
        return "mostrar/index";
    }
}
