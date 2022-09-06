package com.proyecto.laligapremier.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_pedido" , nullable = false)
    private Long numPedido;

    @Column(name = "unidades" , nullable = false)
    private Integer unidades;

    @Column(name = "precio_final")
    private Integer precioFinal;


}
