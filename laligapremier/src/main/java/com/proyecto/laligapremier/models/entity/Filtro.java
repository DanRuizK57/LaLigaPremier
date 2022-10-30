package com.proyecto.laligapremier.models.entity;

import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;
import com.proyecto.laligapremier.models.enums.TipoPrecio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


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
