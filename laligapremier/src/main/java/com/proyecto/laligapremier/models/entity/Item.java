package com.proyecto.laligapremier.models.entity;

import com.proyecto.laligapremier.models.enums.Talla;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "nombre_jugador")
    private String nombreJugador;

    @Column(name = "odigo")
    private String codigo;

    @Column(name = "talla")
    @Enumerated(EnumType.STRING)
    private Talla talla;

    @Column(name = "cantidad_camisetas")
    private Integer cantidad;

}
