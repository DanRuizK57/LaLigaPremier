package com.proyecto.laligapremier.models.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String correo;
    private String clave;
    private String roles;
    private String telefono;
    private String direccion;


    public Usuario() {
    }

    public Usuario(String nombre, String correo, String clave, String roles) {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.roles = roles;
    }

    public Usuario(String correo, String clave, String roles) {
        this.correo = correo;
        this.clave = clave;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", clave='" + clave + '\'' +
                ", roles='" + roles + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
