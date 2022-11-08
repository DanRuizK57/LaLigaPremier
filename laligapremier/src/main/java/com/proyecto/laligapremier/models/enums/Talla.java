package com.proyecto.laligapremier.models.enums;

import com.proyecto.laligapremier.models.entity.Camiseta;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Clase tipo Enum de Talla
 * Nos permite crear objetos de tipo Camiseta con consistencia de datos al momento de ingresar la talla de este.
 *
 * Las clases tipo Enum en la aplicación son importantes, ya que estos nos permite generar distintas instancias
 * de objetos que tengan atributos ya definidos en el mundo real y que haya consistencia en estos.
 * Los Getter de la clase fueron creados con la etiqueta lombok.
 */
@Getter
public enum Talla {
    XS ("XS"),
    S ("S"),
    M ("M"),
    L("L"),
    XL("XL"),
    XXL("XXL");

    private final String talla;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Camiseta> camisetas;

    /**
     * Método constructor de Talla.
     * @param talla parámetro de tipo String, usado para indicar la talla en la camiseta.
     */
    Talla(String talla){
        this.talla = talla; 
    }
}
