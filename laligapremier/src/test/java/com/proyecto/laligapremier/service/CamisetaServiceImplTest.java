package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.entity.Camiseta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CamisetaServiceImplTest {

    @Autowired
    private CamisetaServiceImpl service = new CamisetaServiceImpl();

    @Test
    @DisplayName("Lista de equipos no es nula")
    void listarEquipos_T1 () {
        List<Camiseta> equipos = service.listarEquipos();
        assertNotNull(equipos);
    }
    @Test
    @DisplayName("Lista de equipos no esta vacia")
    void listarEquipos_T2 () {
        List<Camiseta> equipos = service.listarEquipos();
        assertFalse(equipos.isEmpty());
    }
    @Test
    @DisplayName("La lista equipos contiene SOLO camisetas tipo equipos")
    void listarEquipos_T3 () {
        List<Camiseta> equipos = service.listarEquipos();

        List<Camiseta> selecciones = service.listarEquipos()
                .stream()
                .filter(p -> p.getTipoCamiseta().getTipo().equalsIgnoreCase("seleccion"))
                .toList();
        assertTrue(selecciones.isEmpty());

    }


    @Test
    void listarSelecciones() {
    }
}