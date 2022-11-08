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

/**
 * Clase controladora de la vista principal de la aplicacion.
 */

@Controller
public class IndexController {

    /**
     * Inyeccion de interfaces de la logica de servicios para usar la logica de la aplicacion.
     */
    @Autowired
    private ICamisetaService camisetaService;

    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Metodo que muestra elementos de la base de datos y la aplicacion a la vista principal.
     * @param page parametro de tipo int, usado para el paginador.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista principal de la aplicacion
     */
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

        model.addAttribute("titulo" , "Camisetas disponibles");
        model.addAttribute("camisetas" , camisetas);
        model.addAttribute("page", pageRender);
        model.addAttribute("marcas" , Marca.values());
        model.addAttribute("tallas" , Talla.values());
        model.addAttribute("precios", TipoPrecio.values());
        model.addAttribute("objetoFiltro" , new Filtro());

        return "mostrar/index";
    }

    /**
     * Metodo para usar un filtro personalizado en la vista principal
     * @param objetoFiltro parametro de tipo Filtro, utilizado como auxiliar para aplicar el filtro.
     * @param result parametro de tipo BindingResult, usado para validar el objeto y contener errores que pueden producirse
     * @param page parametro de tipo int, usado para el paginador.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista principal de la aplicacion.
     */
    @PostMapping("/filtro")
    public String ejecutarFiltro(
            @Valid @ModelAttribute("objetoFiltro") Filtro objetoFiltro,
            BindingResult result,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model,
            Principal principal){

        Pageable pageRequest = PageRequest.of(page, 6);

        Page<Camiseta> camisetasPage = camisetaService.listarPorFiltros(objetoFiltro, pageRequest);

        PageRender<Camiseta> pageRender = new PageRender<>("/filtro", camisetasPage);

        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.addAttribute("userId", userId);
        }

        model.addAttribute("camisetas" , camisetasPage);
        model.addAttribute("titulo" , "Camisetas disponibles");
        model.addAttribute("marcas" , Marca.values());
        model.addAttribute("tallas" , Talla.values());
        model.addAttribute("precios", TipoPrecio.values());
        model.addAttribute("page", pageRender);
        return "mostrar/index";
    }
}
