package com.proyecto.laligapremier.config;

import com.proyecto.laligapremier.models.entity.CustomOAuth2User;
import com.proyecto.laligapremier.service.IUsuarioService;
import com.proyecto.laligapremier.service.impl.CustomOAuth2UserService;
import com.proyecto.laligapremier.service.impl.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity // Habilitar seguridad web
@EnableMethodSecurity
public class SecurityConfig {
    // Configurar Spring Security para que no esté por defecto

    // Creación de usuarios:
    // 1. InMemoryUserDetailsManager: Crear un usuario en memoria
    // 2. JpaUserDetailsManager: Crear usuario en JPA

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Autowired
    private IUsuarioService usuarioService;

    private final JpaUserDetailsService jpaUserDetailsService;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    // Filtro de seguridad
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // No permite solicitudes a menos que el usuario esté registrado
                .authorizeRequests(consulta -> consulta
                        // Se añaden excepciones, donde se podrá ingresar sin autenticación
                        .mvcMatchers("/", "/index", "/registro", "/oauth/**", "/iniciar-sesion",
                                "/nosotros", "/carrito-de-compras", "/selecciones",
                                "/equipos", "/ver-camiseta/{id}", "/uploads/{filename:.+}",
                                "/busqueda", "/recuperar-contraseña",
                                // Cargar archivos ccs e imágenes
                                "/css/**", "/image/**", "/js/**", "/resources/sql/**").permitAll()
                        .anyRequest().authenticated())
                // Así spring security identifica como obtener los datos de los usuarios
                .userDetailsService(jpaUserDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .oauth2Login()
                    .loginPage("/iniciar-sesion")
                .userInfoEndpoint()
                .userService(oauthUserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {

                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                        usuarioService.processOAuthPostLogin(oauthUser.getEmail());

                        response.sendRedirect("/");
                    }
                }).and().build();
    }

    //Cifrar contraseña
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
