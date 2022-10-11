package com.proyecto.laligapremier.models.enums;

import lombok.Getter;

@Getter
public enum Marca {
    ADIDAS ("Adidas"),
    NIKE ("Nike"), 
    PUMA ("Puma");

    private final String nombre; 
    
    Marca(String nombre){
        this.nombre = nombre; 
    }
}
