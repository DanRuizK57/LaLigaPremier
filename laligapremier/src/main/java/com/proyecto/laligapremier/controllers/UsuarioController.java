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
 * Clase controladora del usuario, utilizada para iniciar sesión, el registro, guardar, editar y recuperar contraseña de
 * un usuario.
 */
@Controller
@SessionAttributes("usuario")
public class UsuarioController {

    /**
     * Inyección de interfaces de la lógica de servicios para usar la lógica de la aplicación.
     */

    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Método para el inicio de sesión con el usuario.
     * @param error parámetro de tipo String, usado para verificar si existe error al iniciar sesión.
     * @param logout parámetro de tipo String, usado para verificar que el usuario ha cerrado sesión.
     * @param model parámetro de tipo Model, usado para recibir o entregar parámetros desde una vista.
     * @param principal parámetro de tipo Principal, usado para obtener al usuario en la sesión activa.
     * @param flash parámetro de tipo RedirectAttributes usado para mostrar mensajes flash en la vista.
     * @return retorna la vista de inicio de sesión.
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
     * Método que registra un usuario a la aplicación.
     * @param model parámetro de tipo Model, usado para recibir o entregar parámetros desde una vista.
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
     * Método que guarda un usuario en la base de datos.
     * @param usuario parámetro de tipo Usuario, usado para guardarlo en la base de datos.
     * @param result parámetro de tipo BindingResult, usado para validar el objeto y contener errores que pueden producirse.
     * @param model parámetro de tipo Model, usado para recibir o entregar parámetros desde una vista.
     * @param status parámetro de tipo SessionStatus usado para indicar que el estado de la sesión está completa.
     * @param flash parámetro de tipo RedirectAttributes usado para mostrar mensajes flash en la vista.
     * @return vista del registro de la aplicación.
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
     * Método que edita el perfil del usuario, actualizando parámetro de estos.
     * @param id parámetro de tipo Long, usado para obtener el usuario solicitado desde la base de datos.
     * @param model parámetro de tipo Model, usado para recibir o entregar parámetros desde una vista.
     * @param flash parámetro de tipo RedirectAttributes usado para mostrar mensajes flash en la vista.
     * @param principal parámetro de tipo Principal, usado para obtener al usuario en la sesión activa.
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
     * Método que guarda el perfil editado en la base de datos.
     * @param usuario parámetro de tipo Usuario, usado para guardar los parámetros en la base de datos.
     * @param result parámetro de tipo BindingResult, usado para validar el objeto y contener errores que pueden producirse.
     * @param model parámetro de tipo Model, usado para recibir o entregar parámetros desde una vista.
     * @param flash parámetro de tipo RedirectAttributes usado para mostrar mensajes flash en la vista.
     * @param status parámetro de tipo SessionStatus usado para indicar que el estado de la sesión está completa.
     * @param principal parámetro de tipo Principal, usado para obtener al usuario en la sesión activa.
     * @return vista principal de la aplicación.
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
     * Método encargado del cambio de contraseña del usuario.
     * @param id parámetro de tipo Long, usado para obtener el usuario desde la base de datos.
     * @param model parámetro de tipo Model, usado para recibir o entregar parámetros desde una vista.
     * @param flash parámetro de tipo RedirectAttributes usado para mostrar mensajes flash en la vista.
     * @param principal parámetro de tipo Principal, usado para obtener al usuario en la sesión activa.
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
     * Método que guarda la nueva contraseña en la base de datos.
     * @param usuario parámetro de tipo Usuario, usado para obtener las contraseñas y guardarlas a la base de datos.
     * @param result parámetro de tipo BindingResult, usado para validar el objeto y contener errores que pueden producirse.
     * @param model parámetro de tipo Model, usado para recibir o entregar parámetros desde una vista.
     * @param flash parámetro de tipo RedirectAttributes usado para mostrar mensajes flash en la vista
     * @param status parámetro de tipo SessionStatus usado para indicar que el estado de la sesión está completa.
     * @param principal parámetro de tipo Principal, usado para obtener al usuario en la sesión activa.
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
     * Método para validar que principal no sea null.
     * @param model parámetro de tipo Model, usado para recibir o entregar parámetros desde una vista.
     * @param principal parámetro de tipo Principal, usado para obtener al usuario en la sesión activa.
     */
    private void principalNotNull(Model model, Principal principal) {
        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.addAttribute("userId", userId);
        }
    }


}
