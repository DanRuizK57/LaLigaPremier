package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.models.entity.Usuario;
import com.proyecto.laligapremier.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private IUsuarioService IUsuarioService;

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

        if(IUsuarioService.compararClaves(usuario.getClave() , usuario.getRepetirClave())){
            //Pasar nueva contraseña cifrada
            usuario.setClave((IUsuarioService.cifrarClave(usuario.getClave())));

            usuario.setRoles("ROLE_USER");

            IUsuarioService.guardar(usuario);
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
    public String editarPerfil(@PathVariable(value = "id") Long id, Model model , RedirectAttributes flash) {

        Usuario usuario = null;
        if(id>0){
            usuario = IUsuarioService.findOne(id);
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
            SessionStatus status
    ){
        if(result.hasErrors()){
            model.addAttribute("titulo" , "Editar Perfil");
        }

            String mensajeFlash = (usuario.getId() != null) ? "¡Usuario editado con éxito!" : "¡Usuario agregado con éxito!";
            IUsuarioService.guardar(usuario);
            status.setComplete();
            flash.addFlashAttribute("info" , mensajeFlash);

        return "redirect:/";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value="/cambiar-contraseña")
    public String cambiarContraseña(Model model) {
        model.addAttribute("titulo" , "Cambiar Contraseña");
        return "cuenta/cambiar-contraseña";
    }
    @GetMapping(value="/recuperar-contraseña")
    public String recuperarContraseña(Model model) {
        model.addAttribute("titulo" , "Recuperar Contraseña");
        return "cuenta/recuperar-contraseña";
    }

}
