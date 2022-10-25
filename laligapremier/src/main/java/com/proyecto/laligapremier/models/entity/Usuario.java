package com.proyecto.laligapremier.models.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;
    @NotBlank
    @Email
    private String correo;
    @NotBlank
    private String clave;
    private String roles;
    private String telefono;
    private String direccion;
    private String repetirClave;

    private String nuevaClave;

    //@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private List<Pedido> pedidos;

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

    public Usuario(String nombre, String correo, String clave, String repetirClave, String roles) {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.repetirClave = repetirClave;
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
