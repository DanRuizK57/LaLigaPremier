package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.models.entity.Usuario;
import com.proyecto.laligapremier.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

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
    @GetMapping(value="/editar-perfil")
    public String editarPerfil(Model model) {
        model.addAttribute("titulo" , "Editar Perfil");
        return "cuenta/editar-perfil";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value="/cambiar-contraseña")
    public String cambiarContraseña(Model model) {
        model.addAttribute("titulo" , "Cambiar Contraseña");
        return "cuenta/cambiar-contraseña";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value="/recuperar-contraseña")
    public String recuperarContraseña(Model model) {
        model.addAttribute("titulo" , "Recuperar Contraseña");
        return "cuenta/recuperar-contraseña";
    }

}
