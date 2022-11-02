package com.proyecto.laligapremier.models.enums;

import lombok.Getter;

@Getter
public enum TipoPrecio {

    PRECIO_1("30000"),
    PRECIO_2("50000"),
    PRECIO_3("80000"),
    PRECIO_4("100000");


    private final String precio;

    TipoPrecio(String precio) {
        this.precio = precio;
    }

}
