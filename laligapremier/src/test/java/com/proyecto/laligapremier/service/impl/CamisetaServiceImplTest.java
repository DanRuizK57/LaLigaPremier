package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.Usuario;
import com.proyecto.laligapremier.models.enums.TipoCamiseta;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CamisetaServiceImplTest {

    @Autowired
    private CamisetaServiceImpl camisetaService;

    @BeforeEach
    void inicioPrueba(TestInfo testInfo) {
        System.out.println("**** Iniciando " + testInfo.getDisplayName() + " ****");
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void findOne() {
    }

    @Test
    void save() {
    }

    @Test
    void findByNombre() {
    }

    @Test
    void listarPorFiltros() {
    }

    @Test
    @DisplayName("Lista de equipos no es nula")
    void listarEquipos_T1 () {
        List<Camiseta> equipos = camisetaService.listarPorTipo(TipoCamiseta.EQUIPO);
        assertNotNull(equipos);
    }
    @Test
    @DisplayName("Lista de equipos no esta vacia")
    void listarEquipos_T2 () {
        List<Camiseta> equipos = camisetaService.listarPorTipo(TipoCamiseta.EQUIPO);
        assertFalse(equipos.isEmpty());
    }
    @Test
    @DisplayName("La lista equipos contiene SOLO camisetas tipo equipos")
    void listarEquipos_T3 () {
        List<Camiseta> seleccionesEnEquipos = camisetaService.listarPorTipo(TipoCamiseta.EQUIPO)
                .stream()
                .filter(p -> p.getTipoCamiseta().getTipo().equalsIgnoreCase("seleccion"))
                .toList();
        assertTrue(seleccionesEnEquipos.isEmpty());

    }
    @Test
    @DisplayName("Lista de selecciones no es nula")
    void listarSelecciones_T1 () {
        List<Camiseta> selecciones = camisetaService.listarPorTipo(TipoCamiseta.SELECCION);
        assertNotNull(selecciones);
    }
    @Test
    @DisplayName("Lista de selecciones no esta vacia")
    void listarSelecciones_T2 () {
        List<Camiseta> selecciones = camisetaService.listarPorTipo(TipoCamiseta.SELECCION);
        assertFalse(selecciones.isEmpty());
    }
    @Test
    @DisplayName("La lista selecciones contiene SOLO camisetas tipo selecciones")
    void listarSelecciones_T3 () {

        List<Camiseta> equipoEnSelecciones = camisetaService.listarPorTipo(TipoCamiseta.SELECCION)
                .stream()
                .filter(p -> p.getTipoCamiseta().getTipo().equalsIgnoreCase("equipo"))
                .toList();
        assertTrue(equipoEnSelecciones.isEmpty());

    }

    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
    }
}