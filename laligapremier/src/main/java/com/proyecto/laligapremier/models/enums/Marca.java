package com.proyecto.laligapremier.models.enums;

import lombok.Getter;

/**
 * Clase tipo Enum de Marca
 * Nos permite crear objetos de tipo Camiseta con consistencia de datos al momento de ingresar la marca de este.
 *
 * Las clases tipo Enum en la aplicación son importantes, ya que estos nos permite generar distintas instancias
 * de objetos que tengan atributos ya definidos en el mundo real y que haya consistencia en estos.
 * Los Getter de la clase fueron generados con la etiqueta lombok.
 */
@Getter
public enum Marca {
    ADIDAS ("Adidas"),
    NIKE ("Nike"), 
    PUMA ("Puma");

    private final String nombre;

    /**
     * Método constructor de Marca.
     * @param nombre parámetro de tipo String, usado para indicar el nombre de la marca en la camiseta.
     */
    Marca(String nombre){
        this.nombre = nombre; 
    }
}
