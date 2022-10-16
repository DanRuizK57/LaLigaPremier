package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.dao.IUsuarioDao;
import com.proyecto.laligapremier.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

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

}
