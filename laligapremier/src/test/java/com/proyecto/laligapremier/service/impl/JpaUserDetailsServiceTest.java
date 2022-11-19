package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.dao.IUsuarioDao;
import com.proyecto.laligapremier.models.entity.Usuario;
import com.proyecto.laligapremier.models.entity.UsuarioSecurity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
    @DisplayName("Comprar usuario estático y obtenido de la base de datos")
    void loadUserByUsername_T1() {
     usuarioService.guardar(usuario);
     UsuarioSecurity usuario2 = (UsuarioSecurity) jpaUserDetailsService.loadUserByUsername("usuario@mail.cl");
     assertEquals(usuario.getCorreo(), usuario2.getCorreo());
    }

    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
    }
}