package com.proyecto.laligapremier.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioServiceImplTest {

    @BeforeEach
    void inicioPrueba(TestInfo testInfo) {
        System.out.println("**** Iniciando " + testInfo.getDisplayName() + " ****");
    }

    @Test
    void listar() {
    }

    @Test
    void findOne() {
    }

    @Test
    void guardar() {
    }

    @Test
    void cifrarClave() {
    }

    @Test
    void findByNombre() {
    }

    @Test
    void findByCorreo() {
    }

    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
    }
}