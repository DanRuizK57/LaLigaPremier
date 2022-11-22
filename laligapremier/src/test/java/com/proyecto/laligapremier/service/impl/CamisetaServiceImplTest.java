package com.proyecto.laligapremier.service.impl;


import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;
import com.proyecto.laligapremier.models.enums.TipoCamiseta;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;


import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CamisetaServiceImplTest {

    @Autowired
    private CamisetaServiceImpl camisetaService;

    private List<Camiseta> camisetas;

    private int size;
    private static Camiseta camiseta;

    private Camiseta camisetaTest;


    @BeforeEach
    void inicioPrueba(TestInfo testInfo) {
        System.out.println("**** Iniciando " + testInfo.getDisplayName() + " ****");
        camisetas =   camisetaService.findAll();
        size = camisetas.size();
        System.out.println(" **** Lista creada ");

        System.out.println("**** Camiseta test creada");
        crearCamisetaTest();


        camiseta = camisetaService.findOne(5L);


    }
    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
        camisetas = null;
    }

    private void crearCamisetaTest() {
        camisetaTest = new Camiseta();
        camisetaTest.setId(30L);
        camisetaTest.setTipoCamiseta(TipoCamiseta.EQUIPO);
        camisetaTest.setMarca(Marca.NIKE);
        camisetaTest.setLiga("Liga premier");
        camisetaTest.setDorsal(10);
        camisetaTest.setPrecio(10000);
        camisetaTest.setJugador("HALAND");
        camisetaTest.setDescripcion("camiseta de haland");
        camisetaTest.setNombre("camiseta haland");
        camisetaTest.setImagen("");
        camisetaTest.setTemporada("2022");
        camisetaTest.setTalla(Talla.S);
        camisetaTest.setEquipo("colocolo el mas grande");
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
    void findAll_T4(){
        var camisetaTest4 = new Camiseta();
        camisetaTest4.setId(30L);
        camisetaTest4.setTipoCamiseta(TipoCamiseta.EQUIPO);
        camisetaTest4.setMarca(Marca.NIKE);
        camisetaTest4.setLiga("Liga premier");
        camisetaTest4.setDorsal(10);
        camisetaTest4.setPrecio(10000);
        camisetaTest4.setJugador("HALAND");
        camisetaTest4.setDescripcion("camiseta de haland");
        camisetaTest4.setNombre("camiseta haland");
        camisetaTest4.setImagen("");
        camisetaTest4.setTemporada("2022");
        camisetaTest4.setTalla(Talla.S);
        camisetaTest4.setEquipo("colocolo el mas grande");

        var tamaño = camisetaService.findAll().size();
        camisetaService.save(camisetaTest4);

        var tamañoFinal = camisetaService.findAll().size();

        assertNotEquals(tamañoFinal, tamaño);

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

        var tamaño = camisetaService.findAll().size();


        var camisetaTest4 = new Camiseta();
        camisetaTest4.setId(31L);
        camisetaTest4.setTipoCamiseta(TipoCamiseta.EQUIPO);
        camisetaTest4.setMarca(Marca.NIKE);
        camisetaTest4.setLiga("Liga premier");
        camisetaTest4.setDorsal(10);
        camisetaTest4.setPrecio(10000);
        camisetaTest4.setJugador("HALAND");
        camisetaTest4.setDescripcion("camiseta de haland");
        camisetaTest4.setNombre("camiseta haland");
        camisetaTest4.setImagen("");
        camisetaTest4.setTemporada("2022");
        camisetaTest4.setTalla(Talla.S);
        camisetaTest4.setEquipo("colocolo el mas grande");

        var camisetaTest5 = new Camiseta();
        camisetaTest5.setId(32L);
        camisetaTest5.setTipoCamiseta(TipoCamiseta.EQUIPO);
        camisetaTest5.setMarca(Marca.NIKE);
        camisetaTest5.setLiga("Liga premier");
        camisetaTest5.setDorsal(10);
        camisetaTest5.setPrecio(10000);
        camisetaTest5.setJugador("HALAND");
        camisetaTest5.setDescripcion("camiseta de haland");
        camisetaTest5.setNombre("camiseta haland");
        camisetaTest5.setImagen("");
        camisetaTest5.setTemporada("2022");
        camisetaTest5.setTalla(Talla.S);
        camisetaTest5.setEquipo("colocolo el mas grande");

        camisetaService.save(camisetaTest4);
        camisetaService.save(camisetaTest5);

        camisetaService.delete(7L);
        camisetaService.delete(8L);


        var tamañoActual = camisetaService.findAll().size();

        assertEquals(tamañoActual , tamaño);
    }


    @Test
    @DisplayName("objeto obtenido no es null")
    void findOne_T1() {
        assertNotNull(camiseta);
    }

    @Test
    @DisplayName("Camiseta obtenida desde la base de datos tiene parametros no nulos")
    void findOne_T2() {
        assertNotNull(camiseta.getId());
        assertNotNull(camiseta.getPrecio());
        assertNotNull(camiseta.getMarca());
        assertNotNull(camiseta.getTalla());
        assertNotNull(camiseta.getDescripcion());
        assertNotNull(camiseta.getTipoCamiseta());
        assertNotNull(camiseta.getNombre());
        assertNotNull(camiseta.getEquipo());
        assertNotNull(camiseta.getJugador());
        assertNotNull(camiseta.getLiga());
        assertNotNull(camiseta.getTemporada());
        assertNotNull(camiseta.getDorsal());
    }


    @Test
    @DisplayName("No se puede obtener camiseta con ID inexistente")
    void findOne_T3() {
        Camiseta camisetaNoExistente = camisetaService.findOne(20L);
        assertNull(camisetaNoExistente);
    }


    @Test
    @DisplayName("Obtenemos el objeto que estamos buscando")
    void findOne_T4() {
        Long id = 5L;
        assertEquals(id , camiseta.getId());
    }

    @Test
    @DisplayName("El producto se guarda en la base de datos correctamente")
    void save_T1() {
        camisetaService.save(camisetaTest);
        var camisetaGuardad = camisetaService.findOne(30L);
        assertNotNull(camisetaGuardad);

    }
    @Test
    @DisplayName("No deja guardar un producto sin parametros en la base de datos")
    void save_T2() {
        Camiseta camisetaNueva = new Camiseta();
        assertThrows(ConstraintViolationException.class , ()->{
            camisetaService.save(camisetaNueva);
        });
    }

    @Test
    @DisplayName("Lanza exeption cuando el producto tiene precio negativo")
    void save_T3() {
        Camiseta camisetaNueva = new Camiseta();
        camisetaNueva.setId(12L);
        camisetaNueva.setTipoCamiseta(TipoCamiseta.EQUIPO);
        camisetaNueva.setMarca(Marca.NIKE);
        camisetaNueva.setLiga("Liga premier");
        camisetaNueva.setDorsal(10);
        camisetaNueva.setPrecio(-10000);
        camisetaNueva.setJugador("HALAND");
        camisetaNueva.setDescripcion("camiseta de haland");
        camisetaNueva.setNombre("camiseta haland");
        camisetaNueva.setImagen("a");
        camisetaNueva.setTemporada("2022");
        camisetaNueva.setTalla(Talla.S);
        camisetaNueva.setEquipo("colocolo el mas grande");
        assertThrows(ConstraintViolationException.class, () ->{
            camisetaService.save(camisetaNueva);
        });

    }
    @Test
    @DisplayName("ID no puede ser null al guardar")
    void save_T4() {
        Camiseta camisetaNueva = new Camiseta();
        camisetaNueva.setId(null);
        camisetaNueva.setTipoCamiseta(TipoCamiseta.EQUIPO);
        camisetaNueva.setMarca(Marca.NIKE);
        camisetaNueva.setLiga("Liga premier");
        camisetaNueva.setDorsal(10);
        camisetaNueva.setPrecio(-10000);
        camisetaNueva.setJugador("HALAND");
        camisetaNueva.setDescripcion("camiseta de haland");
        camisetaNueva.setNombre("camiseta haland");
        camisetaNueva.setImagen("a");
        camisetaNueva.setTemporada("2022");
        camisetaNueva.setTalla(Talla.S);
        camisetaNueva.setEquipo("colocolo el mas grande");
        assertThrows(ConstraintViolationException.class, () ->{
            camisetaService.save(camisetaNueva);
        });
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