package com.proyecto.laligapremier.models.enums;

import lombok.Getter;

/**
 * Clase tipo Enum de TipoCamiseta
 * Nos permite crear objetos de tipo Camiseta con consistencia de datos al momento de ingresar el tipo de la camiseta.
 *
 * Las clases tipo Enum en la aplicación son importantes, ya que estos nos permite generar distintas instancias
 * de objetos que tengan atributos ya definidos en el mundo real y que haya consistencia en estos.
 * Los Getter de la clase fueron creados con la etiqueta lombok.
 */
@Getter
public enum TipoCamiseta {
    SELECCION("Seleccion"),
    EQUIPO("Equipo");

    private final String tipo;

    /**
     * Constructor de la clase TipoCamiseta.
     * @param tipo parámetro de tipo String, usado para ingresar el tipo de la camiseta que se va a crear.
     */
    TipoCamiseta(String tipo) {
        this.tipo = tipo;
    }
}
