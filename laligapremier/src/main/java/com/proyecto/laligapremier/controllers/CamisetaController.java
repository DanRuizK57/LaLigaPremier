package com.proyecto.laligapremier.controllers;
import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;
import com.proyecto.laligapremier.models.enums.TipoCamiseta;
import com.proyecto.laligapremier.service.ICamisetaService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.proyecto.laligapremier.service.IUploadFileService;
import com.proyecto.laligapremier.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("camiseta")
public class CamisetaController {
    @Autowired
    private ICamisetaService camisetaService;

    @Autowired
    private IUploadFileService uploadFileService;


    @GetMapping(value = "/selecciones")
    public String ListarSelecciones(@RequestParam(name = "page", defaultValue = "0") int page, Model model){
        Pageable pageRequest = PageRequest.of(page, 6);

        Page<Camiseta> camisetas = camisetaService.listarSelecciones(pageRequest);

        PageRender<Camiseta> pageRender = new PageRender<>("/selecciones", camisetas);

        model.addAttribute("titulo" , "Listado de camisetas de selecciones");
        model.addAttribute("camisetas" , camisetas);
        model.addAttribute("page", pageRender);
        return "mostrar/selecciones";
    }

    @GetMapping(value = "/equipos")
    public String ListarEquipos(@RequestParam(name = "page", defaultValue = "0") int page, Model model){
        Pageable pageRequest = PageRequest.of(page, 6);

        Page<Camiseta> camisetas = camisetaService.listarEquipos(pageRequest);

        PageRender<Camiseta> pageRender = new PageRender<>("/equipos", camisetas);

        model.addAttribute("titulo" , "Listado de camisetas de equipos");
        model.addAttribute("camisetas" , camisetas);
        model.addAttribute("page", pageRender);
        return "mostrar/equipos";
    }

    /**
     * Metodo controlador encargado de la buscar una camiseta consultada por el id
     * Metodo que hay que revisar, debido a que puede tener multiples vistas dependiendo de la camiseta
     * @param id
     * @param model
     * @param flash
     * @return vista que muestra las camisetas
     */
    @GetMapping(value = "/ver-camiseta/{id}")
    public String verCamiseta(@PathVariable(value = "id")Long id, Map <String, Object> model , RedirectAttributes flash){
        Camiseta camiseta = camisetaService.findOne(id);
        if(camiseta == null){
            flash.addAttribute("error" , "Camiseta no encontrada");
            return "redirect:/mostrar/equipos";
        }
        model.put("camiseta", camiseta);
        model.put("titulo", "detalle de la camiseta " + camiseta.getEquipo() + "del jugador " + camiseta.getJugador());
        return "camiseta/ver_camiseta";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/form/{id}")
    public String editarCamiseta(@PathVariable(value = "id") Long id,Map<String , Object> model , RedirectAttributes flash){
        Camiseta camiseta = null;
        if(id>0){
            camiseta = camisetaService.findOne(id);
            if(camiseta==null){
                flash.addFlashAttribute("error" , "La camiseta no existe en la base de datos");
                return "redirect:/";
            }
        }
        else {
            flash.addAttribute("error" , "el id de la camiseta no puede ser 0");
            return "redirect:/";
        }
        model.put("camiseta" , camiseta);
        model.put("tipoCamisetas", TipoCamiseta.values());
        model.put("tallas", Talla.values());
        model.put("marcas", Marca.values());
        model.put("titulo" , "Editar camiseta");

        return "camiseta/form_camiseta";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/form")
    public String crearCamiseta(Map<String , Object>model){    
        Camiseta camiseta = new Camiseta(); 
        model.put("camiseta" , camiseta); 
        model.put("titulo", "Agregar Camiseta");
        model.put("tipoCamisetas", TipoCamiseta.values());
        model.put("tallas", Talla.values());
        model.put("marcas", Marca.values());
        return "camiseta/form_camiseta";
    }
    @PostMapping(value = "/form")
    public String guardar(
        @Valid Camiseta camiseta,
        BindingResult result,
        Model model,
        @RequestParam("file") MultipartFile imagen,
        RedirectAttributes flash,
        SessionStatus status
    ){
        if(result.hasErrors()){
            model.addAttribute("titulo" , "Agregar camiseta");
        }
        if(!imagen.isEmpty()) {
            if (camiseta.getId() != null
                    && camiseta.getId() > 0
                    && camiseta.getImagen() != null
                    && camiseta.getImagen().length() > 0) uploadFileService.delete(camiseta.getImagen());


            String nombreUnico = null;
            try {
                nombreUnico = uploadFileService.copy(imagen);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String mensajeFlash = (camiseta.getId() != null) ? "Camiseta editada con exito" : "¡Camiseta agregada con exito!";
            camiseta.setImagen(nombreUnico);
            camisetaService.save(camiseta);
            status.setComplete();
            flash.addFlashAttribute("info" , "Has subido correctamente '" + nombreUnico + "'");
            flash.addFlashAttribute("info" , mensajeFlash);
        }

        return "redirect:/";
    }

    @GetMapping(value="/uploads/{filename:.+}")
    public ResponseEntity<Resource> verImagen(@PathVariable String filename){
        Resource recurso = null;
        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
                .ok()
                .header(HttpHeaders
                        .CONTENT_DISPOSITION, "attachment; filename=\""
                        + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id , RedirectAttributes flash){
        if(id > 0 ){
            Camiseta camiseta = camisetaService.findOne(id);
            camisetaService.delete(id);
            flash.addFlashAttribute("success" , "Camiseta eliminada con exito");
            if(uploadFileService.delete(camiseta.getImagen()))
                flash.addFlashAttribute(
                        "info"
                        ,"Imagen "
                                + camiseta.getImagen() +
                                " eliminad con existo" );
            }
        return "redirect:/";
    }

    @GetMapping("/busqueda")
    public String buscarCamisetas(Model model, @RequestParam(value = "query", required = false) String q) {
        List<Camiseta> camisetas = camisetaService.findByNombre(q);
        model.addAttribute("camisetas", camisetas);
        model.addAttribute("titulo", "Resultados de búsqueda:");
        return "mostrar/busqueda";
    }


}
