package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.exceptions.UsuarioNoEncontradoException;
import com.proyecto.laligapremier.models.entity.Usuario;
import com.proyecto.laligapremier.service.IUsuarioService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
public class RecuperarClaveController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/recuperar-contraseña")
    public String mostrarOlvidarContraseña(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cuenta/recuperar-contraseña";
    }

    @PostMapping("/recuperar-contraseña")
    public String generarToken(@Valid Usuario usuario, BindingResult result, Model model) {
        String correo = usuario.getCorreo();
        String token = RandomString.make(30);

        try {
            usuarioService.actualizarToken(token, correo);
            String link = "http://localhost:8080/nueva-contraseña?token=" + token;
            enviarCorreo(correo, link);
            model.addAttribute("message", "Te enviamos al correo un link para cambiar tu contraseña. Por favor revísalo.");

        } catch (UsuarioNoEncontradoException ex) {
            model.addAttribute("error", ex.getMessage());
            return "cuenta/recuperar-contraseña";
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error mientras se enviaba el correo");
            return "cuenta/recuperar-contraseña";
        }

        return "cuenta/mensaje-enviado";
    }

    public void enviarCorreo(String correo, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje);

        helper.setFrom("soporte.laligapremier.01@gmail.com", "Soporte LaLiga Premier");
        helper.setTo(correo);

        String asunto = "Link para cambiar tu contraseña";

        String contenido = "<p>Hola,</p>"
                + "<p>Has solicitado recuperar tu contraseña</p>"
                + "<p>Has clic en el link para cambiar tu contraseña:</p>"
                + "<p><a href=\"" + link + "\">Cambiar mi contraseña</a></p>"
                + "<br>"
                + "<p>Ignora este correo si recuerdas tu contraseña "
                + "o si no hiciste esta petición</p>";

        helper.setSubject(asunto);

        helper.setText(contenido, true);

        mailSender.send(mensaje);
    }


    @GetMapping("/nueva-contraseña")
    public String formularioCambiarContraseña(@Param(value = "token") String token, Model model) {
        Usuario usuario = usuarioService.obtenerPorToken(token);
        model.addAttribute("token", token);
        model.addAttribute("usuarioClave", new Usuario());

        if (usuario == null) {
            model.addAttribute("error", "Token Incorrecto");
            return "redirect:/recuperar-contraseña";
        }

        return "cuenta/cambiar-contraseña-olvidada";
    }

    @PostMapping("/nueva-contraseña")
    public String cambiarContraseña(@Valid @ModelAttribute("usuarioClave") Usuario usuarioClave,
                                    BindingResult result,
                                    HttpServletRequest request,
                                    Model model,
                                    RedirectAttributes flash) {
        String token = request.getParameter("token");
        System.out.println("**** TOKEN: " + token);
        String password = request.getParameter("password");

        Usuario usuario = usuarioService.obtenerPorToken(token);
        model.addAttribute("titulo", "Cambia tu contraseña");


        if (usuario == null) {
            model.addAttribute("error", "Token Incorrecto");
            return "redirect:/recuperar-contraseña";
        }
        System.out.println("**** NUEVA CLAVE: " + usuarioClave.getNuevaClave());
        System.out.println("**** CLAVE REPETIDA: " + usuarioClave.getRepetirClave());
        System.out.println("**** COMPARACIÓN: " + usuarioClave.getNuevaClave().equals(usuarioClave.getRepetirClave()));


        if (usuarioService.compararClaves(usuarioClave.getNuevaClave(), usuarioClave.getRepetirClave())) {
            usuarioService.actualizarClave(usuario, usuarioClave.getNuevaClave());

            flash.addFlashAttribute("success", "¡Has cambiado tu contraseña correctamente!");
            return "redirect:/iniciar-sesion";
        }else {
            model.addAttribute("error", "¡Las contraseñas no coinciden!");
            model.addAttribute("token", token);
            model.addAttribute("usuario", new Usuario());
            return "cuenta/cambiar-contraseña-olvidada";
        }

    }

}
