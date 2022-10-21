package com.proyecto.laligapremier.config;

import com.proyecto.laligapremier.service.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Habilitar seguridad web
@EnableMethodSecurity
public class SecurityConfig {
    // Configurar Spring Security para que no esté por defecto

    // Creación de usuarios:
    // 1. InMemoryUserDetailsManager: Crear un usuario en memoria
    // 2. JpaUserDetailsManager: Crear usuario en JPA

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
                        .mvcMatchers("/", "/index", "/registro",
                                "/nosotros", "/carrito-de-compras", "/selecciones",
                                "/equipos", "/ver-camiseta/{id}", "/uploads/{filename:.+}",
                                "/busqueda", "/recuperar-contraseña",
                                // Cargar archivos ccs e imágenes
                                "/css/**", "/image/**", "/js/**").permitAll()
                        .anyRequest().authenticated())
                // Así spring security identifica como obtener los datos de los usuarios
                .userDetailsService(jpaUserDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                // Autenticación básica HTTP, puede ser así (logeo básico) o un form de inicio de sesión
                .formLogin((formulario) -> formulario
                        // permite acceso a todos
                        .loginPage("/iniciar-sesion")
                        // Utilizar otro atributo para iniciar sesión
                        .usernameParameter("correo")
                        .permitAll()
                )
                .logout((cerrarSesion) -> cerrarSesion.permitAll())
                .build();
    }

    //Cifrar contraseña
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
