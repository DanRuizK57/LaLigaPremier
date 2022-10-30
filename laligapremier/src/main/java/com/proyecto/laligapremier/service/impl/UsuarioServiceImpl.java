package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.exceptions.UsuarioNoEncontradoException;
import com.proyecto.laligapremier.models.dao.IUsuarioDao;
import com.proyecto.laligapremier.models.entity.Usuario;
import com.proyecto.laligapremier.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) usuarioDao.findAll();
    }

    @Override
    public Optional<Usuario> listarId(Long id) {
        return usuarioDao.findById(id);
    }

    @Override
    public Usuario findOne(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Override
    public void guardar(Usuario u) {
        usuarioDao.save(u);
    }

    @Override
    public String cifrarClave(String clave) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(clave);
    }

    @Override
    public Usuario findByNombre(String nombre) {
        return usuarioDao.findByNombre(nombre);
    }

    @Override
    public Usuario findByCorreo(String correo) {
        return usuarioDao.findByCorreo(correo).orElse(null);
    }

    @Override
    public boolean compararClavesActuales(String claveActual, String claveEncriptada) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(claveActual, claveEncriptada);
    }

    @Override
    public boolean compararClaves(String clave, String repetirClave) {
        return clave.equals(repetirClave);
    }

    @Override
    public void actualizarToken(String token, String correo) throws UsuarioNoEncontradoException {
        Usuario usuario = findByCorreo(correo);
        if (usuario != null) {
            usuario.setTokenRecuperarClave(token);
            usuarioDao.save(usuario);
        } else {
            throw new UsuarioNoEncontradoException(correo);
        }
    }

    @Override
    public Usuario obtenerPorToken(String token) {
        return usuarioDao.findByTokenRecuperarClave(token);
    }

    @Override
    public void actualizarClave(Usuario usuario, String nuevaClave) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String claveEncriptada = passwordEncoder.encode(nuevaClave);
        usuario.setClave(claveEncriptada);

        usuario.setTokenRecuperarClave(null);
        usuarioDao.save(usuario);
    }

}
