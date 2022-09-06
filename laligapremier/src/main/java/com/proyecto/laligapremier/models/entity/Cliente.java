package com.proyecto.laligapremier.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Cliente{
    @Id
    @Column(name = "rut" , nullable = false)
    private String rut;

    @Column(name = "email" , nullable = false)
    private String email ;

    @Column(name = "contraseña" , nullable = false)
    private String contraseña;

    @Column(name = "nombre", nullable = false)
    private String nombre ;

    @Column(name = "direccion" , nullable = false)
    private String direccion ;

    @Column(name= "telefono", nullable = false)
    private String telefono;
}
