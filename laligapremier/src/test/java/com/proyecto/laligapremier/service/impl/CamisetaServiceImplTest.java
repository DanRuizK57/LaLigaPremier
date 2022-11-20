package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.exceptions.CamisetaNoEncontradaException;
import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;
import com.proyecto.laligapremier.models.enums.TipoCamiseta;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CamisetaServiceImplTest {

    @Autowired
    private CamisetaServiceImpl camisetaService;

    private List<Camiseta> camisetas;

    private int size;
    private static Camiseta camiseta;


    @BeforeEach
    void inicioPrueba(TestInfo testInfo) {
        System.out.println("**** Iniciando " + testInfo.getDisplayName() + " ****");
        camisetas =   camisetaService.findAll();
        size = camisetas.size();
        System.out.println(" **** Lista creada ");

        System.out.println("**** Camiseta test creada");
        CrearCamisetaTest();
    }
    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
        camisetas = null;
    }

    private void CrearCamisetaTest() {
        camiseta = new Camiseta();
        camiseta.setId(10L);
        camiseta.setTipoCamiseta(TipoCamiseta.EQUIPO);
        camiseta.setMarca(Marca.NIKE);
        camiseta.setLiga("Liga premier");
        camiseta.setDorsal(10);
        camiseta.setPrecio(10000);
        camiseta.setJugador("HALAND");
        camiseta.setDescripcion("camiseta de haland");
        camiseta.setNombre("camiseta haland");
        camiseta.setImagen("");
        camiseta.setTemporada("2022");
        camiseta.setTalla(Talla.S);
        camiseta.setEquipo("colocolo el mas grande");
        camisetaService.save(camiseta);
    }

    @Test
    @DisplayName("Trae una lista que no esta vacia")
    void findAll_T1() {
        assertNotNull(camisetas);
    }
    @Test
    @DisplayName("La lista de camistas no esta vacia")
    void findAll_T2() {
        assertTrue(camisetas.size()>0);
    }
    @Test
    @DisplayName("lista obtenida esta vacia ")
    void findAll_T3() {
        assertFalse(camisetas.isEmpty());
    }


    @Test
    @DisplayName("Una camiseta es eliminada correctamente")
    void delete_T1() {
        camisetaService.delete(1L);
        assertNull(camisetaService.findOne(1L));
    }

    @Test
    @DisplayName("La lista obtenida desde la base de datos disminuye al eliminar")
    void delete_T2() {
        camisetaService.delete(2L);
        var sizeComparar = camisetaService.findAll().size();
        assertNotEquals(sizeComparar , size);
    }

    @Test
    @DisplayName("Aplicacion lanza exception cuando ocurre un error")
    void delete_T3() {
        camisetaService.delete(3L);
        assertThrows(EmptyResultDataAccessException.class, () -> {
           camisetaService.delete(3L);
        });
    }

    @Test
    @DisplayName("Aplicacion lanza exception cuando ocurre un error")
    void delete_T4() {
        // TODO : ENCONTRAR UN POSIBLE TEST DELETE 4
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
    void listarEquipos_T1() {
        List<Camiseta> equipos = camisetaService.listarPorTipo(TipoCamiseta.EQUIPO);
        assertNotNull(equipos);
    }

    @Test
    @DisplayName("Lista de equipos no esta vacia")
    void listarEquipos_T2() {
        List<Camiseta> equipos = camisetaService.listarPorTipo(TipoCamiseta.EQUIPO);
        assertFalse(equipos.isEmpty());
    }

    @Test
    @DisplayName("La lista equipos contiene SOLO camisetas tipo equipos")
    void listarEquipos_T3() {
        List<Camiseta> seleccionesEnEquipos = camisetaService.listarPorTipo(TipoCamiseta.EQUIPO)
                .stream()
                .filter(p -> p.getTipoCamiseta().getTipo().equalsIgnoreCase("seleccion"))
                .toList();
        assertTrue(seleccionesEnEquipos.isEmpty());

    }

    @Test
    @DisplayName("Lista de selecciones no es nula")
    void listarSelecciones_T1() {
        List<Camiseta> selecciones = camisetaService.listarPorTipo(TipoCamiseta.SELECCION);
        assertNotNull(selecciones);
    }

    @Test
    @DisplayName("Lista de selecciones no esta vacia")
    void listarSelecciones_T2() {
        List<Camiseta> selecciones = camisetaService.listarPorTipo(TipoCamiseta.SELECCION);
        assertFalse(selecciones.isEmpty());
    }

    @Test
    @DisplayName("La lista selecciones contiene SOLO camisetas tipo selecciones")
    void listarSelecciones_T3() {

        List<Camiseta> equipoEnSelecciones = camisetaService.listarPorTipo(TipoCamiseta.SELECCION)
                .stream()
                .filter(p -> p.getTipoCamiseta().getTipo().equalsIgnoreCase("equipo"))
                .toList();
        assertTrue(equipoEnSelecciones.isEmpty());

    }


}