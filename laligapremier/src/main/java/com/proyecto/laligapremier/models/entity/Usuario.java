package com.proyecto.laligapremier.models.entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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

    @Column(name = "token_recuperar_clave")
    private String tokenRecuperarClave;

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

    public Usuario(String correo) {
        this.correo = correo;
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
