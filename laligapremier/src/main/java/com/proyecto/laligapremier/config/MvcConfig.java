package com.proyecto.laligapremier.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * Clase de Configuracion MvcConfig. Nos permite guardar imagenes en rutas externas al proyecto.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Metodo que agrega directorios a nuestro proyecto.
     * @param registry parametro de tipo ResourceHandlerRegistry que nos ayuda a registrar una nueva ruta para los recursos
     * estaticos.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();

        log.debug(resourcePath);

        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);

    }
}
