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

@Controller
@SessionAttributes("usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

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

    @GetMapping(value="/registro")
    public String registro(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("titulo" , "Registrar Cuenta");
        model.addAttribute("usuario", usuario);
        return "cuenta/registro";
    }

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

    private void principalNotNull(Model model, Principal principal) {
        if (principal != null) {
            int userId = Math.toIntExact(usuarioService.findByNombre(principal.getName()).getId());
            model.addAttribute("userId", userId);
        }
    }


}
