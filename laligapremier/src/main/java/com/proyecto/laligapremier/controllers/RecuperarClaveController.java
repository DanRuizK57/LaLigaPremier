package com.proyecto.laligapremier.controllers;

import com.proyecto.laligapremier.exceptions.UsuarioNoEncontradoException;
import com.proyecto.laligapremier.models.entity.Usuario;
import com.proyecto.laligapremier.models.entity.Utility;
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
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cuenta/recuperar-contraseña";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(@Valid Usuario usuario, BindingResult result, HttpServletRequest request, Model model) {
        String correo = usuario.getCorreo();
        String token = RandomString.make(30);

        try {
            usuarioService.updateResetPasswordToken(token, correo);
            String link = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(correo, link);
            model.addAttribute("message", "Te enviamos al correo un link para cambiar tu contraseña. Por favor revísalo.");

        } catch (UsuarioNoEncontradoException ex) {
            model.addAttribute("error", ex.getMessage());
            System.out.println("********** Usuario no encontrado: " + ex);
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error mientras se enviaba el correo");
            System.out.println("********** Excepción encontrada: " + e);
        }

        return "cuenta/mensaje-enviado";
    }

    public void sendEmail(String correo, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje);

        helper.setFrom("laliga.premier01@gmail.com", "Soporte LaLiga Premier");
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


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        Usuario usuario = usuarioService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (usuario == null) {
            model.addAttribute("message", "Token Incorrecto");
            return "message";
        }

        return "cuenta/cambiar-contraseña-olvidada";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Usuario usuario = usuarioService.getByResetPasswordToken(token);
        model.addAttribute("title", "Cambia tu contraseña");

        if (usuario == null) {
            model.addAttribute("message", "Token Incorrecto");
            return "redirect:/forgot_password";
        } else {
            usuarioService.updatePassword(usuario, password);

            model.addAttribute("message", "¡Has cambiado tu contraseña correctamente!");
            return "redirect:/";
        }

    }

}
