package com.proyecto.laligapremier.controllers;
import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.ItemPedido;
import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;
import com.proyecto.laligapremier.models.enums.TipoCamiseta;
import com.proyecto.laligapremier.service.ICamisetaService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;;
import java.util.Map;
import javax.validation.Valid;

import com.proyecto.laligapremier.service.IUploadFileService;
import com.proyecto.laligapremier.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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

/**
 * Clase controladora de la entidad camiseta. Enruta, muestra, agrega y borra camisetas.
 */
@Controller
@SessionAttributes("camiseta")
public class CamisetaController {

    /**
     * Inyeccion de interfaces de la logica de servicios para usar la logica de la aplicacion.
     */

    @Autowired
    private ICamisetaService camisetaService;
    @Autowired
    private IUploadFileService uploadFileService;
    @Autowired
    private IUsuarioService usuarioService;


    /**
     * Metodo controlador encargado de la buscar una camiseta consultada por el id
     * Recibe como parametro el id para despues buscarlo en la base de datos
     * @param id parametro de tipo Long, usado para encontrar el objeto en la base de datos
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista
     * @param flash parametro de tipo RedirectAttributesm usado para mostrar mensajes flash en la vista
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista que muestra las camisetas
     */
    @GetMapping(value = "/ver-camiseta/{id}")
    public String verCamiseta(@PathVariable(value = "id")Long id, Map <String, Object> model , RedirectAttributes flash, Principal principal){

        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.put("userId", userId);
        }

        ItemPedido item = new ItemPedido();

        Camiseta camiseta = camisetaService.findOne(id);

        if(camiseta == null){
            flash.addAttribute("error" , "Camiseta no encontrada");
            return "redirect:/mostrar/equipos";
        }
        model.put("tallas", Talla.values());
        model.put("camiseta", camiseta);
        model.put("item", item);
        return "camiseta/ver_camiseta";
    }

    /**
     *
     * Metodo que edita camiseta solicitada por el id para posteriormente.
     * @param id parametro de tipo Long, usado para encontrar el objeto en la base de datos.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param flash parametro de tipo RedirectAttributesm usado para mostrar mensajes flash en la vista.
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista del formulario crear camiseta.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/form/{id}")
    public String editarCamiseta(@PathVariable(value = "id") Long id,
                                 Map<String , Object> model ,
                                 RedirectAttributes flash,
                                 Principal principal){

        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.put("userId", userId);
        }

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

    /**
     * Metodo que crea una camiseta para posteriormente guardarla en la base de datos
     * @param model parametro de tipo Map, usado para recibir o entregar parametros desde una vista.
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista del formulario
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/form")
    public String crearCamiseta(Map<String , Object> model, Principal principal){

        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.put("userId", userId);
        }

        Camiseta camiseta = new Camiseta(); 
        model.put("camiseta" , camiseta); 
        model.put("titulo", "Agregar Camiseta");
        model.put("tipoCamisetas", TipoCamiseta.values());
        model.put("tallas", Talla.values());
        model.put("marcas", Marca.values());
        return "camiseta/form_camiseta";
    }

    /**
     * Metodo encargado de almacenar una camiseta en la base de datos
     * @param camiseta parametro tipo Camiseta, usado para guardar la camiseta en la base de datos
     * @param result parametro de tipo BindingResult, usado para validar el objeto y contener errores que pueden producirse
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param imagen parametro de tipo MultiPartFile, usado para guardar la camiseta con una imagen en la base de datos
     * @param flash parametro de tipo RedirectAttributesm usado para mostrar mensajes flash en la vista.
     * @param status parametro de tipo SessionStatusm usado para indicar que el estado de la sesion esta completa
     * @return vista principal de la aplicacion
     */
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
            model.addAttribute("tipoCamisetas", TipoCamiseta.values());
            model.addAttribute("tallas", Talla.values());
            model.addAttribute("marcas", Marca.values());
            return "camiseta/form_camiseta";
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
            String mensajeFlash = (camiseta.getId() != null) ? "Camiseta editada con éxito" : "¡Camiseta agregada con éxito!";
            camiseta.setImagen(nombreUnico);
            camisetaService.save(camiseta);
            status.setComplete();
            flash.addFlashAttribute("info" , "Has subido correctamente '" + nombreUnico + "'");
            flash.addFlashAttribute("info" , mensajeFlash);
        }
        String mensajeFlash = (camiseta.getId() != null) ? "Camiseta editada con éxito" : "¡Camiseta agregada con éxito!";
        camisetaService.save(camiseta);
        status.setComplete();
        flash.addFlashAttribute("info" , mensajeFlash);
        return "redirect:/";
    }

    /**
     * Metodo muestra la imagen de cada camiseta
     * @param filename parametro de tipo String que indica la ruta de la imagen
     * @return objeto ResponseEntity
     */
    @GetMapping(value="/uploads/{filename:.+}")
    public ResponseEntity<Resource> verImagen(@PathVariable String filename){
        Resource recurso;
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

    /**
     * Metodo que elimina una camiseta de la base de datos
     * @param id parametro de tipo Long, usado para encontrar el objeto en la base de datos.
     * @param flash parametro de tipo RedirectAttributesm usado para mostrar mensajes flash en la vista.
     * @return vista principal de la aplicacion
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id , RedirectAttributes flash){
        if(id > 0 ){
            Camiseta camiseta = camisetaService.findOne(id);
            camisetaService.delete(id);
            flash.addFlashAttribute("success" , "Camiseta eliminada con éxito");
            if(uploadFileService.delete(camiseta.getImagen()))
                flash.addFlashAttribute(
                        "info"
                        ,"Imagen "
                                + camiseta.getImagen() +
                                " eliminada con éxito" );
        }
        return "redirect:/";
    }

}
