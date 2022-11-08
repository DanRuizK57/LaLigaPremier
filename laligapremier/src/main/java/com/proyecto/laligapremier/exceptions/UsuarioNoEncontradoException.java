package com.proyecto.laligapremier.exceptions;

/**
 * Clase Exception UsuarioNoEncontradoException. Creada para lanzar un exception al momento de no encontrar un usuario
 * por el correo, con el fin de recuperar una contraseña.
 */
public class UsuarioNoEncontradoException extends RuntimeException {

    /**
     * Constructor de la Exception personalizada.
     * @param email parámetro de tipo String, usado para lanzar el error con el mensaje pertinente.
     */
    public UsuarioNoEncontradoException(String email){
        super("El usuario con el email " + email +  " no fue encontrado");
    }

}
