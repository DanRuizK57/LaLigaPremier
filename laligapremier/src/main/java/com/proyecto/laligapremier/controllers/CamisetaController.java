package com.proyecto.laligapremier.controllers;
import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.service.ICamisetaService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/selecciones")
    public String ListarSelecciones(Model model){
        model.addAttribute("titulo" , "Listado de camisetas de selecciones");
        model.addAttribute("camisetasSelecciones" , camisetaService.findAll());
        return "mostrar/selecciones";
    }

    @GetMapping(value = "/equipos")
    public String ListarEquipos(Model model){
        model.addAttribute("titulo" , "Listado de camisetas de equipos");
        model.addAttribute("camisetasEquipos" , camisetaService.findAll());
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
    @GetMapping(value = "/ver-camiseta")
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

    @RequestMapping(value = "/camiseta/form_camiseta")
    public String crearCamiseta(Map<String , Object>model){    
        Camiseta camiseta = new Camiseta(); 
        model.put("camiseta" , camiseta); 
        model.put("titulo", "Agregar Camiseta");
        return "camiseta/form_camiseta"; 
    
    }
    @RequestMapping(value = "/camiseta/ form_camiseta", method = RequestMethod.POST)
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
        if(!imagen.isEmpty()){
            String nombreUnico = UUID.randomUUID().toString() + "_" + imagen.getOriginalFilename();
            Path rootPath = Paths.get("uploads").resolve(nombreUnico);
            Path rootAbsolutePath = rootPath.toAbsolutePath();
            log.info("rootPath: " + rootPath);
            log.info("rootAbsolutePath: " + rootAbsolutePath);

            try {
                Files.copy(imagen.getInputStream() , rootAbsolutePath);
                flash.addAttribute("info" , "Has subido correctamente '" + nombreUnico + "'");

                camiseta.setImagen(nombreUnico);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return "redirect:/mostrar/index_admin"; 
    }

    @GetMapping(value="/uploads/{filename:.+}")
    public ResponseEntity<Resource> verImagen(@PathVariable String filename){
        Path pathImagen = Paths.get("uploads").resolve(filename).toAbsolutePath();
        log.info("pathImagen " + pathImagen);
        Resource recurso = null;

        try {
             recurso = new UrlResource(pathImagen.toUri());
             if(!recurso.exists() && !recurso.isReadable())
                 throw new RuntimeException("Error : No se puede cargar la imagen " + pathImagen.toString());
        } catch (MalformedURLException e) {

        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION
                , "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    

  

}
