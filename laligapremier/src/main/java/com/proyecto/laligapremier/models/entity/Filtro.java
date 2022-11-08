package com.proyecto.laligapremier.models.entity;

import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;
import com.proyecto.laligapremier.models.enums.TipoPrecio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


/**
 * Clase Entidad <i>@Entity</i> que representa la tabla filtro en la base de datos
 * Getters y Setters fueron generados con la etiqueta de lombok.
 */

@Getter
@Setter
public class Filtro {


    @Enumerated(EnumType.STRING)
    private Talla talla;

    @Enumerated(EnumType.STRING)
    private Marca marca;

    @Enumerated(EnumType.STRING)
    private TipoPrecio precio;





}
