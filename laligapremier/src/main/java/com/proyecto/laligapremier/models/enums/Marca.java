package com.proyecto.laligapremier.models.enums;

public enum Marca {
    ADIDAS ("Adidas"),
    NIKE ("Nike"), 
    PUMA ("Puma");

    private final String nombre; 
    
    Marca(String nombre){
        this.nombre = nombre; 
    }
}
