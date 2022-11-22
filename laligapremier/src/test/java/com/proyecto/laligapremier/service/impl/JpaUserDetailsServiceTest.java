package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.exceptions.UsuarioNoEncontradoException;
import com.proyecto.laligapremier.models.dao.IUsuarioDao;
import com.proyecto.laligapremier.models.entity.Usuario;
import com.proyecto.laligapremier.models.entity.UsuarioSecurity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JpaUserDetailsServiceTest {

    @Autowired
    private UsuarioServiceImpl usuarioService;
    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    private static Usuario usuario;

    @BeforeAll
    static void inicio(){
        usuario = new Usuario("Usuario1", "usuario@mail.cl", "usuario", "ROLE_USER");
    }

    @BeforeEach
    void inicioPrueba(TestInfo testInfo) {
        System.out.println("**** Iniciando " + testInfo.getDisplayName() + " ****");
    }

    @Test
    @DisplayName("Comprobar usuario estático y obtenido de la base de datos")
    void loadUserByUsername_T1() {
     usuarioService.guardar(usuario);
     UsuarioSecurity usuario2 = (UsuarioSecurity) jpaUserDetailsService.loadUserByUsername("usuario@mail.cl");
     assertEquals(usuario.getCorreo(), usuario2.getCorreo());
    }

    @Test
    @DisplayName("Se lanza excepción cuando no encuentra el usuario")
    void loadUserByUsername_T2() {
        assertThrows(UsernameNotFoundException.class, () -> {
            jpaUserDetailsService.loadUserByUsername("usuario2@mail.cl");
        });
    }

    @Test
    @DisplayName("Comprobar usuario encontrado no sea nulo")
    void loadUserByUsername_T3() {
        usuarioService.guardar(usuario);
        UsuarioSecurity usuario2 = (UsuarioSecurity) jpaUserDetailsService.loadUserByUsername("usuario@mail.cl");
        assertNotNull(usuario2);
    }

    @Test
    @DisplayName("Comprobar usuario estático con otro usuario no sean iguales")
    void loadUserByUsername_T4() {
        usuarioService.guardar(usuario);
        Usuario usuario1 = new Usuario("usuario2", "usuario2@mail.cl", "12", "ROLE_USER");
        usuarioService.guardar(usuario1);
        UsuarioSecurity usuario2 = (UsuarioSecurity) jpaUserDetailsService.loadUserByUsername("usuario@mail.cl");
        assertNotEquals(usuario1.getCorreo(), usuario2.getCorreo());
    }

    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
    }
}