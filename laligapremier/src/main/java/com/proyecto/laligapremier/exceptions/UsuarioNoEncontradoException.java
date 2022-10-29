package com.proyecto.laligapremier.exceptions;

public class UsuarioNoEncontradoException extends RuntimeException {

    public UsuarioNoEncontradoException(String email){
        super("El usuario con el email " + email +  " no fue encontarado");
    }

}
