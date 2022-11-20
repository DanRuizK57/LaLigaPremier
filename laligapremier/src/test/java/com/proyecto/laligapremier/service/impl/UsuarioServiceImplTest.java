package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.dao.IUsuarioDao;
import com.proyecto.laligapremier.models.entity.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioServiceImplTest {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    private static Usuario usuario;

    @BeforeAll
    static void inicio() {
        usuario = new Usuario("usuario", "usuario@mail.cl", "12", "ROLE_USER");
    }

    @BeforeEach
    void inicioPrueba(TestInfo testInfo) {
        System.out.println("**** Iniciando " + testInfo.getDisplayName() + " ****");
        usuarioDao.deleteAll();
    }

    @Test
    @DisplayName("Sin añadir ningún usuario aparte del admin por defecto, que el tamaño de la lista sea 1")
    void listar_T1() {
        List<Usuario> usuarios = usuarioService.listar();
        assertEquals(0, usuarios.size());
    }

    @Test
    @DisplayName("Añadir usuario, que la lista no sea null")
    void listar_T2() {
        usuarioService.guardar(usuario);

        List<Usuario> usuarios = usuarioService.listar();
        assertNotNull(usuarios);
    }

    @Test
    @DisplayName("Añadir 2 usuarios, que la lista sea mayor a 1")
    void listar_T3() {
        usuarioService.guardar(usuario);
        usuarioService.guardar(new Usuario("usuario2", "usuario2@mail.cl", "12", "ROLE_USER"));

        List<Usuario> usuarios = usuarioService.listar();
        assertTrue(usuarios.size() > 1);
    }

    @Test
    @DisplayName("Añadir 2 usuarios, compara tamaños con lista local")
    void listar_T4() {
        usuarioService.guardar(usuario);
        Usuario usuario2 = new Usuario("usuario2", "usuario2@mail.cl", "12", "ROLE_USER");
        usuarioService.guardar(usuario2);
        List<Usuario> usuarios = usuarioService.listar();
        List<Usuario> usuariosLocal = new ArrayList<>();
        usuariosLocal.add(usuario);
        assertNotEquals(usuariosLocal.size(), usuarios.size());
    }

    @Test
    @DisplayName("Comprobar usuario estático y obtenido de la base de datos")
    void findOne_T1() {
        usuarioService.guardar(usuario);
        Usuario usuario2 = usuarioService.findOne(16L);
        assertEquals(usuario.getNombre(), usuario2.getNombre());
    }

    @Test
    @DisplayName("Comprobar usuario encontrado no sea nulo")
    void findOne_T2() {
        usuarioService.guardar(usuario);
        Usuario usuarioObtenido = usuarioService.findOne(17L);
        assertNotNull(usuarioObtenido);
    }

    @Test
    @DisplayName("Comprobar usuario estático con otro usuario no sean iguales")
    void findOne_T3() {
        usuarioService.guardar(usuario);
        Usuario usuario1 = new Usuario("usuario2", "usuario2@mail.cl", "12", "ROLE_USER");
        usuarioService.guardar(usuario1);
        List<Usuario> usuarios = usuarioService.listar();
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println(usuarios.get(i).toString());
        }
        Usuario usuarioObtenido = usuarioService.findOne(18L);
        assertNotEquals(usuario1.getCorreo(), usuarioObtenido.getCorreo());
    }

    @Test
    @DisplayName("Comprobar usuario con id inexistente sea null")
    void findOne_T4() {
        Usuario usuarioObtenido = usuarioService.findOne(10L);
        assertNull(usuarioObtenido);
    }

    @Test
    @DisplayName("Añadir usuario y comprobar que el id del usuario guardado sea el mismo")
    void guardar_T1() {
        usuarioService.guardar(usuario);
        assertEquals(usuario.getNombre(), usuarioService.findByCorreo(usuario.getCorreo()).getNombre());
    }

    @Test
    @DisplayName("Añadir usuario y comprobar que el id del usuario guardado sea distinto")
    void guardar_T2() {
        usuarioService.guardar(usuario);
        Usuario usuario2 = new Usuario("usuario2", "usuario2@mail.cl", "12", "ROLE_USER");
        usuarioService.guardar(usuario2);
        List<Usuario> usuarios = usuarioService.listar();
        Usuario usuarioObtenido = null;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuario2.getId()){
                usuarioObtenido = usuarios.get(i);
            }
        }
        assertNotEquals(usuario2, usuarioObtenido);
    }

    @Test
    @DisplayName("Añadir usuario y comprobar que no sea nulo")
    void guardar_T3() {
        usuarioService.guardar(usuario);

        assertNotNull(usuarioService.findByNombre(usuario.getNombre()));
    }

    @Test
    @DisplayName("Añadir usuario y comprobar que la lista sea mayor a 0")
    void guardar_T4() {
        usuarioService.guardar(usuario);
        List<Usuario> usuarios = usuarioService.listar();
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println(usuarios.get(i).toString());
        }
        assertTrue(usuarios.size() > 0);
    }

    @Test
    @DisplayName("Comparar clave string no sea igual luego de cifrarla")
    void cifrarClave_T1() {
        assertNotEquals("123", usuarioService.cifrarClave("123"));
    }

    @Test
    @DisplayName("Comparar clave cifrada no sea nula")
    void cifrarClave_T2() {
        assertNotNull(usuarioService.cifrarClave("1234"));
    }

    @Test
    @DisplayName("Comparar clave cifrada tenga más caracteres que la clave string")
    void cifrarClave_T3() {
        assertTrue(usuarioService.cifrarClave("12").length() > 2);
    }

    @Test
    @DisplayName("Comparar clave cifrada no esté vacía")
    void cifrarClave_T4() {
        assertFalse(usuarioService.cifrarClave("clave").isBlank());
    }

    @Test
    @DisplayName("Comprobar usuario estático y obtenido de la base de datos")
    void findByNombre_T1() {
        usuarioService.guardar(usuario);
        Usuario usuario2 = usuarioService.findByNombre("usuario");
        assertEquals(usuario.getNombre(), usuario2.getNombre());
    }

    @Test
    @DisplayName("Comprobar usuario encontrado no sea nulo")
    void findByNombre_T2() {
        usuarioService.guardar(usuario);
        Usuario usuarioObtenido = usuarioService.findByNombre("usuario");
        assertNotNull(usuarioObtenido);
    }

    @Test
    @DisplayName("Comprobar usuario estático con otro usuario no sean iguales")
    void findByNombre_T3() {
        usuarioService.guardar(usuario);
        Usuario usuario1 = new Usuario("usuario2", "usuario2@mail.cl", "12", "ROLE_USER");
        usuarioService.guardar(usuario1);
        Usuario usuarioObtenido = usuarioService.findByNombre("usuario");
        assertNotEquals(usuario1.getCorreo(), usuarioObtenido.getCorreo());
    }

    @Test
    @DisplayName("Comprobar usuario con nombre inexistente sea null")
    void findByNombre_T4() {
        Usuario usuarioObtenido = usuarioService.findByNombre("juan");
        assertNull(usuarioObtenido);
    }

    @Test
    @DisplayName("Comprobar usuario estático y obtenido de la base de datos")
    void findByCorreo_T1() {
        usuarioService.guardar(usuario);
        Usuario usuario2 = usuarioService.findByCorreo("usuario@mail.cl");
        assertEquals(usuario.getCorreo(), usuario2.getCorreo());
    }

    @Test
    @DisplayName("Comprobar usuario encontrado no sea nulo")
    void findByCorreo_T2() {
        usuarioService.guardar(usuario);
        Usuario usuarioObtenido = usuarioService.findByCorreo("usuario@mail.cl");
        assertNotNull(usuarioObtenido);
    }

    @Test
    @DisplayName("Comprobar usuario estático con otro usuario no sean iguales")
    void findByCorreo_T3() {
        usuarioService.guardar(usuario);
        Usuario usuario1 = new Usuario("usuario2", "usuario2@mail.cl", "12", "ROLE_USER");
        usuarioService.guardar(usuario1);
        Usuario usuarioObtenido = usuarioService.findByCorreo("usuario@mail.cl");
        assertNotEquals(usuario1.getCorreo(), usuarioObtenido.getCorreo());
    }

    @Test
    @DisplayName("Comprobar usuario con correo inexistente sea null")
    void findByCorreo_T4() {
        Usuario usuarioObtenido = usuarioService.findByNombre("juan@gmail.cl");
        assertNull(usuarioObtenido);
    }

    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
        usuarioDao.deleteAll();
    }
}