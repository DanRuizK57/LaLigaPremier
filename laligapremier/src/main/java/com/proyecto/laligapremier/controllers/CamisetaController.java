package com.proyecto.laligapremier.controllers;
import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.service.ICamisetaService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("camiseta")
public class CamisetaController {
    @Autowired
    private ICamisetaService camisetaService;

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
        model.put("titulo", "detalle de la camiseta ");
        return "camiseta/ver_camiseta";
    }

    @RequestMapping(value = "/camiseta/form_camiseta")
    public String crearCamiseta(Map<String , Object>model){    
        Camiseta camiseta = new Camiseta(); 
        model.put("camiseta" , camiseta); 
        model.put("titulo", "Agregar Camiseta");
        return "camiseta/form_camiseta"; 
    
    }
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
            //falta estudio para implementar la subida de imagen correctamente
        }

        return "redirect:/mostrar/index_admin"; 
    }

    

  

}
