package com.proyecto.laligapremier.models.enums;

import lombok.Getter;

@Getter
public enum TipoPrecio {

    PRECIO_1("10000"),
    PRECIO_2("20000"),
    PRECIO_3("30000"),
    PRECIO_4("40000");


    private final String precio;

    TipoPrecio(String precio) {
        this.precio = precio;
    }

}
