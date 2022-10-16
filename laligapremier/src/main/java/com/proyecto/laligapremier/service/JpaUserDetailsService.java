package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.dao.IUsuarioDao;
import com.proyecto.laligapremier.models.entity.UsuarioSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioDao usuarioDao;

    // Busca si el usuario está registrado
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
       return usuarioDao
               .findByCorreo(correo)
               .map(UsuarioSecurity::new)
               .orElseThrow(() -> new UsernameNotFoundException("Correo no encontrado: " + correo));
    }

}
