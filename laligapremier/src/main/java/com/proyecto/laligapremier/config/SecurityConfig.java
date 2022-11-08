package com.proyecto.laligapremier.config;

import com.proyecto.laligapremier.service.impl.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase encargada de manejar la configuración de Spring Security.
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Inyección de la clase JpaUserDetailsService para acceder a sus métodos.
     */

    private final JpaUserDetailsService jpaUserDetailsService;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    /**
     * Método que establece ciertas características del Spring security como las solicitudes permitidas sin registro,
     * cargar detalles del Usuario, establecer la solicitud del login y el logout.
     * @param http Solicitud HTTP específica de seguridad.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(consulta -> consulta
                        .mvcMatchers("/", "/index", "/registro",
                                "/nosotros", "/carrito-de-compras", "/selecciones",
                                "/equipos", "/ver-camiseta/{id}", "/uploads/{filename:.+}",
                                "/busqueda", "/recuperar-contraseña", "/nueva-contraseña", "/filtro",
                                // Cargar archivos ccs e imágenes
                                "/css/**", "/image/**", "/js/**").permitAll()
                        .anyRequest().authenticated())
                .userDetailsService(jpaUserDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .formLogin((formulario) -> formulario
                        .loginPage("/iniciar-sesion")
                        .usernameParameter("correo")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout((cerrarSesion) -> cerrarSesion.permitAll())
                .build();
    }

    /**
     * Creación del Bean para cifrar contraseñas, utilizado en el arranque de la aplicación.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
