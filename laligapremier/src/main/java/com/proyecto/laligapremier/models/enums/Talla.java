package com.proyecto.laligapremier.models.enums;

import lombok.Getter;

@Getter
public enum Talla {
    XS ("XS"),
    S ("S"),
    M ("M"),
    L("L"),
    XL("XL"),
    XXL("XXL");

    private final String talla;
    
    Talla(String talla){
        this.talla = talla; 
    }
}
