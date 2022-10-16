package com.proyecto.laligapremier.models.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class UsuarioSecurity implements UserDetails {

    private Usuario usuario;

    public UsuarioSecurity(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String getUsername() {
        return usuario.getNombre();
    }

    public String getCorreo(){
        return usuario.getCorreo();
    }

    @Override
    public String getPassword() {
        return usuario.getClave();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(usuario
                .getRoles()
                .split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
