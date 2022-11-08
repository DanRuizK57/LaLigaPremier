package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.dao.IUsuarioDao;
import com.proyecto.laligapremier.models.entity.UsuarioSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementación de la clase de UserDetailsService, donde se dan lógica a sus métodos.
 */

@Service
public class JpaUserDetailsService implements UserDetailsService {

    /**
     * Inyección de la clase IUsuarioDao para acceder a sus métodos y así realizar consultas a la base de datos.
     */
    @Autowired
    private IUsuarioDao usuarioDao;

    /**
     * Método que buscar si el correo pertenece a algún usuario registrado en la base de datos.
     * @param correo Objeto de tipo String, usado para buscar un Usuario.
     * @return Objeto del tipo Usuario.
     */
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
       return usuarioDao
               .findByCorreo(correo)
               .map(UsuarioSecurity::new)
               .orElseThrow(() -> new UsernameNotFoundException("Correo no encontrado: " + correo));
    }

}
