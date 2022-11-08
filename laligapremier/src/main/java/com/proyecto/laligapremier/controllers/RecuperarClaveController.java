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

/**
 * Clase controladora para recuperar contraseña en caso de ser olvidada
 */

@Controller
public class RecuperarClaveController {

    /**
     * Inyeccion de interfaces de la logica de servicios para usar la logica de la aplicacion.
     */

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Metodo que muestra la vista de recuperar contraseña
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @return vista recuperar contraseña
     */
    @GetMapping("/recuperar-contraseña")
    public String mostrarOlvidarContraseña(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cuenta/recuperar-contraseña";
    }

    /**
     * Metodo que genera un token unico para recuperar la contraseña
     * @param usuario parametro de tipo Usuario, usado para crear el token para ese usuario
     * @param result parametro de tipo BindingResult, usado para validar el objeto y contener errores que pueden producirse
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @return retorna vista del mensaje enviado para recuperar contraseña
     */

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

    /**
     * Metodo que envia el correo para recuperar la contraseña
     * @param correo parametro de tipo String, usado para obtener el correo del usuario para recuperar la contraseña.
     * @param link parametro de tipo String, usado para mostrar el link para recuperar contraseña en el mail
     */
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

    /**
     * Metodo que muestra el formulario de recuperar contraseña una vez presionado el link.
     * @param token parametro de tipo String, usado para obtener un usuario por el token.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @return retorna vista del formulario para recuperar contraseña.
     */
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

    /**
     * Metodo utilizado para cambiar la contraseña del usuario
     * @param usuarioClave parametro de tipo Usuario, usado para validar y actualizar la contraseña.
     * @param result parametro de tipo BindingResult, usado para validar el objeto y contener errores que pueden producirse
     * @param request parametro de tipo HttpServeletRequest, usado para obtener los datos de la solicitud actual.
     * @param model parametro de tipo Model, usado para recibir o entregar parametros desde una vista.
     * @param flash parametro de tipo RedirectAttributesm usado para mostrar mensajes flash en la vista.
     * @return vista para cambiar la contraseña olvidada
     */

    @PostMapping("/nueva-contraseña")
    public String cambiarContraseña(@Valid @ModelAttribute("usuarioClave") Usuario usuarioClave,
                                    BindingResult result,
                                    HttpServletRequest request,
                                    Model model,
                                    RedirectAttributes flash) {
        String token = request.getParameter("token");
        Usuario usuario = usuarioService.obtenerPorToken(token);
        model.addAttribute("titulo", "Cambia tu contraseña");


        if (usuario == null) {
            model.addAttribute("error", "Token Incorrecto");
            return "redirect:/recuperar-contraseña";
        }


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
