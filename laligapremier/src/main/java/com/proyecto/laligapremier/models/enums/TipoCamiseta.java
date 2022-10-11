package com.proyecto.laligapremier.models.enums;

import lombok.Getter;


@Getter
public enum TipoCamiseta {
    SELECCION("Seleccion"),
    EQUIPO("Equipo");

    private final String tipo;

    TipoCamiseta(String tipo) {
        this.tipo = tipo;
    }
}
