package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.ItemPedido;
import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;
import com.proyecto.laligapremier.models.enums.TipoCamiseta;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PedidoServiceImplTest {

    @Autowired
    private PedidoServiceImpl pedidoService;

    @BeforeEach
    void inicioPrueba(TestInfo testInfo) {
        System.out.println("**** Iniciando " + testInfo.getDisplayName() + " ****");
    }

    /*@Test
    @DisplayName("Sin añadir ningún item, que el tamaño de la lista sea 0")
    void obtenerItemsDelCarrito_T1() {
        List<ItemPedido> items = carritoService.obtenerItemsDelCarrito();
        assertEquals(0, items.size());
    }

    @Test
    @DisplayName("Añadir item, que la lista no sea null")
    void obtenerItemsDelCarrito_T2() {
        carritoService.añadirItem(itemPedido);

        List<ItemPedido> items = carritoService.obtenerItemsDelCarrito();
        assertNotNull(items);
    }

    @Test
    @DisplayName("Añadir 2 items, que la lista sea mayor a 1")
    void obtenerItemsDelCarrito_T3() {
        carritoService.añadirItem(itemPedido);
        ItemPedido nuevoItem = new ItemPedido(2L,
                new Camiseta(2L, "PSG", "Ligue 1", 10, "Messi",
                        "2022", 40000, " ", "Messi", "Camiseta",
                        Talla.L, Marca.ADIDAS, TipoCamiseta.EQUIPO),
                Talla.L,
                1);
        carritoService.añadirItem(nuevoItem);
        List<ItemPedido> items = carritoService.obtenerItemsDelCarrito();
        assertTrue(items.size() > 1);
    }

    @Test
    @DisplayName("Añadir 2 items, compara tamaños con lista local")
    void obtenerItemsDelCarrito_T4() {
        carritoService.añadirItem(itemPedido);
        ItemPedido nuevoItem = new ItemPedido(2L,
                new Camiseta(2L, "PSG", "Ligue 1", 10, "Messi",
                        "2022", 40000, " ", "Messi", "Camiseta",
                        Talla.L, Marca.ADIDAS, TipoCamiseta.EQUIPO),
                Talla.L,
                1);
        carritoService.añadirItem(nuevoItem);
        List<ItemPedido> items = carritoService.obtenerItemsDelCarrito();
        List<ItemPedido> itemsLocal = new ArrayList<>();
        itemsLocal.add(itemPedido);
        assertNotEquals(itemsLocal.size(), items.size());
    }*/

    @Test
    void delete() {
    }

    @Test
    void findOne() {
    }

    @Test
    void save() {
    }

    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
    }
}