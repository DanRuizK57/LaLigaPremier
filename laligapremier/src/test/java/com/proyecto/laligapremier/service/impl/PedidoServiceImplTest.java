package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.dao.IPedidoDao;
import com.proyecto.laligapremier.models.entity.Pedido;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PedidoServiceImplTest {

    @Autowired
    private IPedidoDao pedidoDao;

    @Autowired
    private PedidoServiceImpl pedidoService;

    private static Pedido pedido;

    @BeforeAll
    static void inicio() {
        pedido = new Pedido(1L, "ABC123");
    }

    @BeforeEach
    void inicioPrueba(TestInfo testInfo) {
        System.out.println("**** Iniciando " + testInfo.getDisplayName() + " ****");
    }

    @Test
    @DisplayName("Sin añadir ningún pedido, que el tamaño de la lista sea 0")
    void findAll_T1() {
        List<Pedido> pedidos = pedidoService.findAll();
        assertEquals(0, pedidos.size());
    }

    @Test
    @DisplayName("Añadir pedido, que la lista no sea null")
    void findAll_T2() {
        pedidoService.save(new Pedido());

        List<Pedido> pedidos = pedidoService.findAll();
        assertNotNull(pedidos);
    }

    // CON PROBLEMAS
    @Test
    @DisplayName("Añadir 2 pedidos, que la lista sea mayor a 1")
    void findAll_T3() {
        pedidoService.save(pedido);
        pedidoService.save(new Pedido());

        List<Pedido> pedidos = pedidoService.findAll();
        assertTrue(pedidos.size() > 1);
    }

    @Test
    @DisplayName("Añadir 2 pedidos, compara tamaños con lista local")
    void findAll_T4() {
        pedidoService.save(new Pedido());
        pedidoService.save(new Pedido());
        List<Pedido> pedidos = pedidoService.findAll();
        List<Pedido> pedidosLocal = new ArrayList<>();
        pedidosLocal.add(new Pedido());
        assertNotEquals(pedidosLocal.size(), pedidos.size());
    }

    @Test
    @DisplayName("Añadir pedido, eliminarlo y verificar que la lista esté vacía")
    void delete_T1() {
        pedidoService.save(new Pedido());
        pedidoService.delete(5L);
        List<Pedido> pedidos = pedidoService.findAll();
        assertTrue(pedidos.size() < 1);
    }

    @Test
    @DisplayName("Añadir pedidos, eliminarlos y verificar que la lista no sea mayor a 1")
    void delete_T2() {
        pedidoService.save(new Pedido());
        pedidoService.save(new Pedido());

        pedidoService.delete(6L);
        pedidoService.delete(7L);
        List<Pedido> pedidos = pedidoService.findAll();
        assertFalse(pedidos.size() > 1);
    }

    @Test
    @DisplayName("Añadir pedidos, eliminar 1 y verificar que no sea el numero de pedidos de un principio")
    void delete_T3() {
        Pedido pedido = new Pedido();
        pedidoService.save(pedido);
        Pedido pedido2 = new Pedido();
        pedidoService.save(pedido2);

        pedidoService.delete(8L);
        List<Pedido> pedidos = pedidoService.findAll();
        //List<Pedido> pedidos = pedidoService.findAll();
        assertNotEquals(2, pedidos.size());
    }

    @Test
    @DisplayName("Añadir pedidos, eliminar todos y verificar que el numero de pedidos sea 0")
    void delete_T4() {
        Pedido pedido = new Pedido();
        pedidoService.save(pedido);
        Pedido pedido2 = new Pedido(2L, "AFGR23");
        pedidoService.save(pedido2);
        pedidoService.delete(10L);
        pedidoService.delete(11L);

        List<Pedido> pedidos = pedidoService.findAll();
        for (int i = 0; i < pedidos.size(); i++) {
            System.out.println(pedidos.get(i).toString());
        }
        assertEquals(0, pedidos.size());
    }

    @Test
    @DisplayName("Comprobar usuario estático y obtenido de la base de datos")
    void findOne_T1() {
        Pedido pedido = new Pedido(1L, "AG45R3");
        pedidoService.save(pedido);
        List<Pedido> pedidos = pedidoService.findAll();
        for (int i = 0; i < pedidos.size(); i++) {
            System.out.println(pedidos.get(i).toString());
        }
        Pedido pedidoObtenido = pedidoService.findOne(13L);
        assertEquals(pedido.getCodigo(), pedidoObtenido.getCodigo());
    }

    @Test
    @DisplayName("Añadir item y comprobar que el id del producto guardado sea el mismo")
    void save_T1() {
        Pedido pedido = new Pedido(1L, "DSF4565RGFR");
        pedidoService.save(pedido);
        List<Pedido> pedidos = pedidoService.findAll();
        Pedido pedidoObtenido = null;
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getCodigo() == pedido.getCodigo()){
                pedidoObtenido = pedidos.get(i);
            }
        }
        assertEquals(pedido.getCodigo(), pedidoObtenido.getCodigo());
    }

    @Test
    @DisplayName("Añadir item y comprobar que el id del producto guardado sea distinto")
    void save_T2() {
        Pedido pedido = new Pedido(1L, "FV45T4");
        pedidoService.save(pedido);
        Pedido pedido2 = new Pedido(2L, "F54GEDF");
        pedidoService.save(pedido);
        List<Pedido> pedidos = pedidoService.findAll();
        Pedido pedidoObtenido = null;
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getId() == pedido2.getId()){
                pedidoObtenido = pedidos.get(i);
            }
        }
        assertNotEquals(pedido2, pedidoObtenido);
    }

    @Test
    @DisplayName("Añadir item y comprobar que no sea nulo")
    void save_T3() {
        Pedido pedido = new Pedido(1L, "32REFGTR");
        pedidoService.save(pedido);
        List<Pedido> pedidos = pedidoService.findAll();
        Pedido pedidoObtenido = null;
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getCodigo() == pedido.getCodigo()){
                pedidoObtenido = pedidos.get(i);
            }
        }
        assertNotNull(pedidoObtenido);
    }

    @Test
    @DisplayName("Añadir item y comprobar que la lista sea mayor a 0")
    void save_T4() {
        Pedido pedido = new Pedido(1L, "VGB5676I");
        pedidoService.save(pedido);
        List<Pedido> pedidos = pedidoService.findAll();
        assertTrue(pedidos.size() > 0);
    }

    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
    }
}