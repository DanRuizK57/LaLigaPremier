package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.models.entity.Usuario;
import com.proyecto.laligapremier.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@SessionAttributes("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value="/iniciar-sesion")
    public String iniciarSesion(Model model) {
        model.addAttribute("titulo" , "Iniciar Sesión");
        return "cuenta/iniciar-sesion";
    }

    @GetMapping(value="/registro")
    public String registro(Model model) {
        model.addAttribute("titulo" , "Registrar Cuenta");
        model.addAttribute("usuario", new Usuario());
        return "cuenta/registro";
    }

    @PostMapping("/registro")
    public String guardarUsuario(@Valid Usuario usuario, SessionStatus status) {

        //Pasar nueva contraseña cifrada
        usuario.setClave(usuarioService.cifrarClave(usuario.getClave()));

        usuario.setRoles("ROLE_USER");

        usuarioService.guardar(usuario);
        status.setComplete();
        return "redirect:/iniciar-sesion";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value="/editar-perfil/{id}")
    public String editarPerfil(@PathVariable(value = "id") Long id, Model model , RedirectAttributes flash) {

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
            SessionStatus status
    ){
        if(result.hasErrors()){
            model.addAttribute("titulo" , "Editar Perfil");
        }

            String mensajeFlash = (usuario.getId() != null) ? "¡Usuario editado con éxito!" : "¡Usuario agregado con éxito!";
            usuarioService.guardar(usuario);
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
