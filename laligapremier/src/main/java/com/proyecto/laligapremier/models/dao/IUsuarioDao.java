package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Clase que establece conexión directa con la base de datos e interactúa con la entidad Usuario.
 */
public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long> {
    Usuario findByNombre(String nombre);

    Optional<Usuario> findByCorreo(String correo);

    Usuario findByTokenRecuperarClave(String token);
}
