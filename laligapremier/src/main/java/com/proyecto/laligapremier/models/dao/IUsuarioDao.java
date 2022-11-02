package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long> {
    Usuario findByNombre(String nombre);

    Optional<Usuario> findByCorreo(String correo);

    Usuario findByTokenRecuperarClave(String token);
}
