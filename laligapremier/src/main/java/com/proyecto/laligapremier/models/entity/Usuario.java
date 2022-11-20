package com.proyecto.laligapremier.models.entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Clase Entidad <i>@Entity</i> que representa la tabla usuarios en la base de datos.
 * Getters y Setters fueron generados con la etiqueta de lombok.
 */

@Entity
@Getter
@Setter
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    /**
     * Metodo constructor sin parametros.
     */
    public Usuario() {
    }

    /**
     * Metodo constructor de usuario con rol dentro de la aplicacion.
     * @param nombre parametro de tipo String, usado para asignar nombre a la nueva instancia de usuario.
     * @param correo parametro de tipo String, usado para asignar un correo a la nueva instancia de usuario.
     * @param clave parametro de tipo String, usado para asignar una contraseña a la nueva instancia de usuario.
     * @param roles parametro de tipo String, usado para asignar el rol a la nueva instancia de usuario.
     */
    public Usuario(String nombre, String correo, String clave, String roles) {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.roles = roles;
    }

    /**
     * Metodo constructor de usuario con rol y sin nombre.
     * @param correo parametro de tipo String, usado para asignar un correo a la nueva instancia de usuario.
     * @param clave parametro de tipo String, usado para asignar una contraseña a la nueva instancia de usuario.
     * @param roles parametro de tipo String, usado para asignar el rol a la nueva instancia de usuario.
     */

    public Usuario(String correo, String clave, String roles) {
        this.correo = correo;
        this.clave = clave;
        this.roles = roles;
    }

    /**
     * Metodo constructor de usuario con rol y parametro para repetir contraseña.
     * @param nombre parametro de tipo String, usado para asignar nombre a la nueva instancia de usuario.
     * @param correo parametro de tipo String, usado para asignar un correo a la nueva instancia de usuario.
     * @param clave parametro de tipo String, usado para asignar una contraseña a la nueva instancia de usuario.
     * @param repetirClave parametro de tipo String, usado para repitir la contraseña que se ingreso.
     * @param roles parametro de tipo String, usado para asignar el rol a la nueva instancia de usuario.
     */

    public Usuario(String nombre, String correo, String clave, String repetirClave, String roles) {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.repetirClave = repetirClave;
        this.roles = roles;
    }

    /**
     * Metodo contructor de usuario solo con parametro de correo.
     * @param correo parametro de tipo String, usado para crear una instancia de usuario solo con correo.
     */

    public Usuario(String correo) {
        this.correo = correo;
    }

    /**
     * Metodo toString de la clase y entidad usuario.
     * @return atributos del usuario en un String.
     */
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
