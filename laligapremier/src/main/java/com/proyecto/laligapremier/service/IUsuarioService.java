package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    public List<Usuario> listar();
    public Optional<Usuario> listarId(Long id);

    Usuario findOne(Long id);
    public void guardar(Usuario u);

    public String cifrarClave(String clave);

    public Usuario findByNombre(String nombre);

    public Usuario findByCorreo(String correo);

    public boolean compararClavesActuales(String claveActual, String claveEncriptada);

    public boolean compararClaves(String clave, String repetirClave);

    public void actualizarToken(String token, String correo);

    public Usuario obtenerPorToken(String token);

    public void actualizarClave(Usuario usuario, String nuevaClave);


}
