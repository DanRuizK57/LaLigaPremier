package com.proyecto.laligapremier.models.enums;

import lombok.Getter;

/**
 * Clase tipo Enum de TipoPrecio
 * Nos permite ingresar los precios para los filtros correctamente en el formulario para filtrar.
 *
 * Las clases tipo Enum en la aplicacion son importantes, ya que estos nos permite crear distitas intancias
 * de objetos que tengan atributos ya definidos en el mundo real y que haya consistencia en estos.
 * Los Getter de la clase fueron creados con la etiqueta lombok
 */
@Getter
public enum TipoPrecio {

    PRECIO_1("30000"),
    PRECIO_2("50000"),
    PRECIO_3("80000"),
    PRECIO_4("100000");


    private final String precio;

    /**
     * Constructor de la clase TipoPrecio
     * @param precio parametro de tipo String, usado para indicar el precio que se va a ingrear en el filtro.
     */
    TipoPrecio(String precio) {
        this.precio = precio;
    }

}
