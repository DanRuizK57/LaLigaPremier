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

/**
 * Implementación de la clase de IUsuarioService, donde se dan lógica a sus métodos.
 */

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    /**
     * Inyección de la clase IUsuarioDao para acceder a sus métodos y así realizar consultas a la base de datos.
     */
    @Autowired
    private IUsuarioDao usuarioDao;

    /**
     * Método que guarda en una lista todos los objetos del tipo Usuario disponibles en la base de datos.
     * @return Lista de objetos del tipo Usuario.
     */
    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) usuarioDao.findAll();
    }

    /**
     * Método que guarda en una lista todos los objetos del tipo Usuario con un mismo id disponibles en la base de datos.
     * @param id Objeto de tipo Long, usado para elegir a la camiseta a eliminar.
     * @return Lista de objetos del tipo Usuario.
     */
    @Override
    public Optional<Usuario> listarId(Long id) {
        return usuarioDao.findById(id);
    }

    /**
     * Método que busca un objeto del tipo Usuario según su id en la base de datos.
     * @param id Objeto de tipo Long, usado para encontrar el usuario seleccionado.
     * @return Objeto del tipo Usuario.
     */
    @Override
    public Usuario findOne(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    /**
     * Método que guarda un objeto del tipo Usuario en la base de datos.
     * @param u Objeto de tipo Usuario, usado para guardarlo en la base de datos.
     */
    @Override
    public void guardar(Usuario u) {
        usuarioDao.save(u);
    }

    /**
     * Método que cifra una clave con la clase BCryptPasswordEncoder.
     * @param clave Objeto de tipo String, usado para ser cifrado.
     * @return Objeto del tipo String, la clave encriptada.
     */
    @Override
    public String cifrarClave(String clave) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(clave);
    }

    /**
     * Método que busca un objeto del tipo Usuario según su nombre de usuario en la base de datos.
     * @param nombre Objeto de tipo String, usado para encontrar el usuario seleccionado.
     * @return Objeto del tipo Usuario.
     */
    @Override
    public Usuario findByNombre(String nombre) {
        return usuarioDao.findByNombre(nombre);
    }

    /**
     * Método que busca un objeto del tipo Usuario según su correo en la base de datos.
     * @param correo Objeto de tipo String, usado para encontrar el usuario seleccionado.
     * @return Objeto del tipo Usuario.
     */
    @Override
    public Usuario findByCorreo(String correo) {
        return usuarioDao.findByCorreo(correo).orElse(null);
    }

    /**
     * Método que compara la actual clave del usuario con otra clave ingresada, valida que sean iguales.
     * @param claveActual Objeto de tipo String, usado para indicar la clave actual del usuario.
     * @param claveEncriptada Objeto de tipo String, usado para indicar la clave ingresada por el usuario.
     * @return Objeto del tipo Boolean.
     */
    @Override
    public boolean compararClavesActuales(String claveActual, String claveEncriptada) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(claveActual, claveEncriptada);
    }

    /**
     * Método que compara una clave ingresada por el usuario con otra para confirmar la nueva contraseña.
     * @param clave Objeto de tipo String, usado para indicar la clave ingresada por el usuario.
     * @param repetirClave Objeto de tipo String, usado para indicar la clave repetida.
     * @return Objeto del tipo Boolean.
     */
    @Override
    public boolean compararClaves(String clave, String repetirClave) {
        return clave.equals(repetirClave);
    }

    /**
     * Método que asigna un token al usuario para poder cambiar su clave a la hora de olvidarla.
     * @param token Objeto de tipo String, usado para asignar el token al usuario.
     * @param correo Objeto de tipo String, usado para buscar al usuario por su correo.
     */
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

    /**
     * Método que busca un objeto del tipo Usuario según su token en la base de datos.
     * @param token Objeto de tipo String, usado para encontrar el usuario seleccionado.
     * @return Objeto del tipo Usuario.
     */
    @Override
    public Usuario obtenerPorToken(String token) {
        return usuarioDao.findByTokenRecuperarClave(token);
    }

    /**
     * Método que reemplaza la clave actual por la nueva y la encripta.
     * @param usuario Objeto de tipo Usuario, usado para seleccionar al usuario al que se le cambiará la contraseña.
     * @param nuevaClave Objeto de tipo String, usado para ser encriptado y asignado al usuario.
     */
    @Override
    public void actualizarClave(Usuario usuario, String nuevaClave) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String claveEncriptada = passwordEncoder.encode(nuevaClave);
        usuario.setClave(claveEncriptada);

        usuario.setTokenRecuperarClave(null);
        usuarioDao.save(usuario);
    }

}
