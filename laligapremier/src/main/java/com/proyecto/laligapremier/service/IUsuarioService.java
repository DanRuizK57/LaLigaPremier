package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de la clase Usuario donde se establecen sus métodos para luego ser implementados
 * en la clase UsuarioServiceImpl.
 */

public interface IUsuarioService {

    List<Usuario> listar();
    Optional<Usuario> listarId(Long id);

    Usuario findOne(Long id);
    void guardar(Usuario u);

    String cifrarClave(String clave);

    Usuario findByNombre(String nombre);

    Usuario findByCorreo(String correo);

    boolean compararClavesActuales(String claveActual, String claveEncriptada);

    boolean compararClaves(String clave, String repetirClave);

    void actualizarToken(String token, String correo);

    Usuario obtenerPorToken(String token);

    void actualizarClave(Usuario usuario, String nuevaClave);


}
