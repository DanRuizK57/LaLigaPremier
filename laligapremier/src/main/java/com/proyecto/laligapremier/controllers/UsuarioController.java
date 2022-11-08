package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.models.entity.Usuario;
import com.proyecto.laligapremier.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Clase controladora del usuario, utilizada para iniciar sesion, el registro, guardar, editar y recuperar contraseña de
 * un usuario.
 */
@Controller
@SessionAttributes("usuario")
public class UsuarioController {

    /**
     * Inyeccion de interfaces de la logica de servicios para usar la logica de la aplicacion.
     */

    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Metodo para el inicio de sesion con el usuario.
     * @param error parametro de tipo String, usado para verificar si existe error al iniciar sesion
     * @param logout parametro de tipo String, usado para verificar que el usuario ha cerrado sesion
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @param flash parametro de tipo RedirectAttributesm usado para mostrar mensajes flash en la vista.
     * @return retorna la vista de inicio de sesion.
     */
    @GetMapping(value="/iniciar-sesion")
    public String iniciarSesion(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model, Principal principal, RedirectAttributes flash) {

        if (principal != null) {
            flash.addFlashAttribute("info", "¡Ya iniciaste sesión anteriormente!");
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("error" , "Correo o contraseña incorrectos, inténtalo nuevamente.");
        }

        if (logout != null) {
            flash.addFlashAttribute("success" , "Has cerrado sesión correctamente.");
            return "redirect:/";
        }

        model.addAttribute("titulo" , "Iniciar Sesión");
        return "cuenta/iniciar-sesion";
    }

    /**
     * Metodo que registra un usuario a la aplicacion
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @return vista registro del usuario.
     */
    @GetMapping(value="/registro")
    public String registro(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("titulo" , "Registrar Cuenta");
        model.addAttribute("usuario", usuario);
        return "cuenta/registro";
    }

    /**
     * Metodo que guarda un usuario en la base de datos.
     * @param usuario parametro de tipo Usuario, usado para guardarlo en la base de datos.
     * @param result parametro de tipo BindingResult, usado para validar el objeto y contener errores que pueden producirse.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param status parametro de tipo SessionStatusm usado para indicar que el estado de la sesion esta completa.
     * @param flash parametro de tipo RedirectAttributesm usado para mostrar mensajes flash en la vista.
     * @return vista del registro de la aplicacion.
     */
    @PostMapping("/registro")
    public String guardarUsuario(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Registrar Cuenta");
            return "cuenta/registro";
        }

        if(usuarioService.compararClaves(usuario.getClave() , usuario.getRepetirClave())){
            //Pasar nueva contraseña cifrada
            usuario.setClave((usuarioService.cifrarClave(usuario.getClave())));
            usuario.setRepetirClave(null);
            usuario.setRoles("ROLE_USER");

            usuarioService.guardar(usuario);
            status.setComplete();
            flash.addFlashAttribute("success" , "¡Te has registrado correctamente!");
            return "redirect:/iniciar-sesion";
        }
        model.addAttribute("error" , "¡Las contraseñas deben coincidir!");
        model.addAttribute("titulo", "Registrar Cuenta");
        return "cuenta/registro";
    }

    /**
     * Metodo que edita el perfil del usuario, actualizando parametro de estos.
     * @param id parametro de tipo Long, usado para obtener el usuario solicitado desde la base de datos.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param flash parametro de tipo RedirectAttributesm usado para mostrar mensajes flash en la vista.
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista para editar el perfil del usuario.
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value="/editar-perfil/{id}")
    public String editarPerfil(@PathVariable(value = "id") Long id, Model model , RedirectAttributes flash, Principal principal) {

        principalNotNull(model, principal);

        Usuario usuario = null;
        if(id>0){
            usuario = usuarioService.findOne(id);
            if(usuario==null){
                flash.addFlashAttribute("error" , "El usuario no existe en la base de datos");
                return "redirect:/";
            }
        }
        else {
            flash.addAttribute("error" , "El id del usuario no puede ser 0");
            return "redirect:/";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo" , "Editar Perfil");

        return "cuenta/editar-perfil";
    }

    /**
     * Metodo que guarda el perfil editado en la base de datos
     * @param usuario parametro de tipo Usuario, usado para guardar los parametros en la base de datos.
     * @param result parametro de tipo BindingResult, usado para validar el objeto y contener errores que pueden producirse.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param flash parametro de tipo RedirectAttributesm usado para mostrar mensajes flash en la vista.
     * @param status parametro de tipo SessionStatusm usado para indicar que el estado de la sesion esta completa.
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista principal de la aplicacion.
     */
    @PostMapping(value = "/perfil")
    public String guardarPerfil(
            @Valid Usuario usuario,
            BindingResult result,
            Model model,
            RedirectAttributes flash,
            SessionStatus status,
            Principal principal
    ){
        if(result.hasErrors()){
            principalNotNull(model, principal);

            model.addAttribute("titulo" , "Editar Perfil");
        }

            String mensajeFlash = (usuario.getId() != null) ? "¡Usuario editado con éxito!" : "¡Usuario agregado con éxito!";
            usuarioService.guardar(usuario);
            Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, usuario.getNombre());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            status.setComplete();
            flash.addFlashAttribute("info" , mensajeFlash);

        return "redirect:/";
    }

    /**
     * Metodo encargado del cambio de contraseña del usuario.
     * @param id parametro de tipo Long, usado para obtener el usuario desde la base de datos.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param flash parametro de tipo RedirectAttributesm usado para mostrar mensajes flash en la vista.
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista para cambiar contraseña.
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value="/cambiar-contraseña/{id}")
    public String cambiarContraseña(@PathVariable(value = "id") Long id, Model model , RedirectAttributes flash, Principal principal) {

        principalNotNull(model, principal);

        Usuario usuario = null;
        if(id>0){
            usuario = usuarioService.findOne(id);
            if(usuario==null){
                flash.addFlashAttribute("error" , "El usuario no existe en la base de datos");
                return "redirect:/";
            }
        }
        else {
            flash.addAttribute("error" , "El id del usuario no puede ser 0");
            return "redirect:/";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo" , "Cambiar Contraseña");
        return "cuenta/cambiar-contraseña";
    }

    /**
     * Metodo que guarda la nueva contraseña en la base de datos.
     * @param usuario parametro de tipo Usuario, usado para obtener las contraseñas y guardarlas a la base de datos.
     * @param result parametro de tipo BindingResult, usado para validar el objeto y contener errores que pueden producirse
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param flash parametro de tipo RedirectAttributesm usado para mostrar mensajes flash en la vista
     * @param status parametro de tipo SessionStatusm usado para indicar que el estado de la sesion esta completa
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     * @return vista para cambiar la contraseña.
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value="/cambiar-contraseña")
    public String guardarNuevaContraseña(@Valid Usuario usuario,
                                         BindingResult result,
                                         Model model,
                                         RedirectAttributes flash,
                                         SessionStatus status,
                                         Principal principal) {

        if(result.hasErrors()){
            principalNotNull(model, principal);

            model.addAttribute("titulo" , "Cambiar Contraseña");
        }

        Usuario usuarioBD = usuarioService.findByNombre(usuario.getNombre());

        String nuevaClave = usuario.getNuevaClave();
        String repetirClave = usuario.getRepetirClave();

        // IF para ver si la contraseña actual coincide con la puesta en el formulario
        if (usuarioService.compararClavesActuales(usuario.getClave(), usuarioBD.getClave())) {
            if (nuevaClave != null && repetirClave != null && !repetirClave.isBlank() && !nuevaClave.isBlank()) {
                if(usuarioService.compararClaves(nuevaClave , repetirClave)){
                    //Pasar nueva contraseña cifrada
                    usuario.setClave((usuarioService.cifrarClave(usuario.getNuevaClave())));

                    usuario.setRoles("ROLE_USER");
                    usuario.setNuevaClave(null);
                    usuario.setRepetirClave(null);

                    usuarioService.guardar(usuario);
                    status.setComplete();
                    flash.addFlashAttribute("success" , "Contraseña cambiada correctamente.");
                    return "redirect:/";
                }else {
                    model.addAttribute("error" , "¡Las contraseñas deben coincidir!");
                    model.addAttribute("titulo" , "Cambiar Contraseña");
                    return "cuenta/cambiar-contraseña";
                }
            }else {
                model.addAttribute("error" , "¡Las contraseñas no pueden estar vacías o nulas!");
                model.addAttribute("titulo" , "Cambiar Contraseña");
                return "cuenta/cambiar-contraseña";
            }

        }

        model.addAttribute("error" , "¡La contraseña actual es incorrecta!");
        model.addAttribute("titulo" , "Cambiar Contraseña");
        return "cuenta/cambiar-contraseña";
    }

    /**
     * Metodo para validad que principal no sea null
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param principal parametro de tipo Principal, usado para obtener al usuario en la sesion activa.
     */
    private void principalNotNull(Model model, Principal principal) {
        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.addAttribute("userId", userId);
        }
    }


}
