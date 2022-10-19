package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    public List<Usuario> listar();
    public Optional<Usuario> listarId(Long id);

    Usuario findOne(Long id);
    public void guardar(Usuario u);

    public String cifrarClave(String clave);

    public Usuario findByNombre(String nombre);

}
