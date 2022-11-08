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

/**
 * Clase controladora de los filtros predeterminados que hay en la aplicacion, muestra las camisetas por selecciones,
 * por equipos (clubes) y tambien busca especifica.
 */
@Controller
@SessionAttributes("camiseta")
public class FiltroController {

    /**
     * Inyeccion de interfaces de la logica de servicios para usar la logica de la aplicacion.
     */

    @Autowired
    private ICamisetaService camisetaService;
    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Metodo que muestra la lista de camisetas de tipo seleccion.
     * @param page parametro de tipo int, usado para el paginador.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista de las selecciones que estan en la base de datos
     */
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
        modelsAtributosVista(model, camisetas, pageRender);
        return "mostrar/selecciones";
    }

    /**
     *Metodo que muestra la lista de camisetas de tipo equipo (clubes)
     * @param page parametro de tipo int, usado para el paginador.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista de los equipos que estan en la base de datos
     */
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
        modelsAtributosVista(model, camisetas, pageRender);
        return "mostrar/equipos";
    }

    /**
     * Metodo que filtra por la barra de busqueda de la aplicacion.
     * @param page parametro de tipo int, usado para el paginador.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param q parametro de tipo String, usado para filtrar las camisetas mediante una "query".
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista de los resultados obtenidos de la busqueda.
     */
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

    /**
     * Metodo que muestra los atributos y camisetas mediante el objeto model, utilizado para evitar tener codigo duplicado
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.el
     * @param camisetas parametro de tipo Page, usado para pasarle las camisetas al model y mostrarlos en la vista
     * @param pageRender parametro de tipo PageRender, usado para determinar el numero de elementos en una vista.
     */
    private static void modelsAtributosVista(Model model, Page<Camiseta> camisetas, PageRender<Camiseta> pageRender) {
        model.addAttribute("camisetas" , camisetas);
        model.addAttribute("page", pageRender);
        model.addAttribute("marcas" , Marca.values());
        model.addAttribute("tallas" , Talla.values());
        model.addAttribute("precios", TipoPrecio.values());
        model.addAttribute("objetoFiltro" , new Filtro());
    }

}
