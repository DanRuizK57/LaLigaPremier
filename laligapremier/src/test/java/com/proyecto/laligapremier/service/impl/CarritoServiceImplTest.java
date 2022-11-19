package com.proyecto.laligapremier.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarritoServiceImplTest {

    @BeforeEach
    void inicioPrueba(TestInfo testInfo) {
        System.out.println("**** Iniciando " + testInfo.getDisplayName() + " ****");
    }

    @Test
    void añadirItem() {
    }

    @Test
    void eliminarItem() {
    }

    @Test
    void obtenerItemsDelCarrito() {
    }


    @Test
    void calcularPrecioTotal() {
    }


    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
    }
}