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
class CarritoServiceImplTest {

    @Autowired
    private CarritoServiceImpl carritoService;

    private static ItemPedido itemPedido;
    @BeforeAll
    static void inicio() {
        itemPedido = new ItemPedido(1L,
                new Camiseta(1L, "Barcelona", "LaLiga", 10, "Messi",
                        "2019", 40000, " ", "Messi", "Camiseta",
                        Talla.L, Marca.ADIDAS, TipoCamiseta.EQUIPO),
                Talla.L,
                2);
    }

    @BeforeEach
    void inicioPrueba(TestInfo testInfo) {
        System.out.println("**** Iniciando " + testInfo.getDisplayName() + " ****");
    }

    @Test
    @DisplayName("Añadir item y comprobar que el id del producto guardado sea el mismo")
    void añadirItem_T1() {
        carritoService.añadirItem(itemPedido);
        List<ItemPedido> items = carritoService.obtenerItemsDelCarrito();
        ItemPedido itemObtenido = null;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == itemPedido.getId()){
                itemObtenido = items.get(i);
            }
        }
        assertEquals(itemPedido, itemObtenido);
    }

    @Test
    @DisplayName("Añadir item y comprobar que el id del producto guardado sea distinto")
    void añadirItem_T2() {
        carritoService.añadirItem(itemPedido);
        ItemPedido nuevoitem = new ItemPedido(2L,
                new Camiseta(2L, "PSG", "Ligue 1", 10, "Messi",
                        "2022", 40000, " ", "Messi", "Camiseta",
                        Talla.L, Marca.ADIDAS, TipoCamiseta.EQUIPO),
                Talla.L,
                1);
        List<ItemPedido> items = carritoService.obtenerItemsDelCarrito();
        ItemPedido itemObtenido = null;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() != nuevoitem.getId()){
                itemObtenido = items.get(i);
            }
        }
        assertNotEquals(nuevoitem, itemObtenido);
    }

    @Test
    @DisplayName("Añadir item y comprobar que no sea nulo")
    void añadirItem_T3() {
        carritoService.añadirItem(itemPedido);
        List<ItemPedido> items = carritoService.obtenerItemsDelCarrito();
        ItemPedido itemObtenido = null;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == itemPedido.getId()){
                itemObtenido = items.get(i);
            }
        }
        assertNotNull(itemObtenido);
    }

    @Test
    @DisplayName("Añadir item y comprobar que la lista sea mayor a 0")
    void añadirItem_T4() {
        carritoService.añadirItem(itemPedido);
        List<ItemPedido> items = carritoService.obtenerItemsDelCarrito();
        assertTrue(items.size() > 0);
    }

    @Test
    @DisplayName("Añadir item, eliminarlo y verificar que la lista esté vacía")
    void eliminarItem_T1() {
        carritoService.añadirItem(itemPedido);
        carritoService.eliminarItem(itemPedido.getId());
        List<ItemPedido> items = carritoService.obtenerItemsDelCarrito();
        assertTrue(items.size() < 1);
    }

    @Test
    @DisplayName("Añadir items, eliminarlos y verificar que la lista no sea mayor a 1")
    void eliminarItem_2() {
        carritoService.añadirItem(itemPedido);
        carritoService.eliminarItem(itemPedido.getId());
        List<ItemPedido> items = carritoService.obtenerItemsDelCarrito();
        assertFalse(items.size() > 1);
    }

    @Test
    @DisplayName("Añadir items, eliminar 1 y verificar que no sea el numero de items de un principio")
    void eliminarItem_T3() {
        carritoService.añadirItem(itemPedido);
        ItemPedido nuevoitem = new ItemPedido(2L,
                new Camiseta(2L, "PSG", "Ligue 1", 10, "Messi",
                        "2022", 40000, " ", "Messi", "Camiseta",
                        Talla.L, Marca.ADIDAS, TipoCamiseta.EQUIPO),
                Talla.L,
                1);
        carritoService.añadirItem(nuevoitem);
        carritoService.eliminarItem(itemPedido.getId());
        List<ItemPedido> items = carritoService.obtenerItemsDelCarrito();
        assertNotEquals(2, items.size());
    }

    @Test
    @DisplayName("Añadir items, eliminar todos y verificar que el numero de items sea 0")
    void eliminarItem_T4() {
        carritoService.añadirItem(itemPedido);
        ItemPedido nuevoitem = new ItemPedido(2L,
                new Camiseta(2L, "PSG", "Ligue 1", 10, "Messi",
                        "2022", 40000, " ", "Messi", "Camiseta",
                        Talla.L, Marca.ADIDAS, TipoCamiseta.EQUIPO),
                Talla.L,
                1);
        carritoService.añadirItem(nuevoitem);
        carritoService.eliminarItem(itemPedido.getId());
        carritoService.eliminarItem(nuevoitem.getId());
        List<ItemPedido> items = carritoService.obtenerItemsDelCarrito();
        assertEquals(0, items.size());
    }

    @Test
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
    }

    @Test
    @DisplayName("Añadir item y comprar precio total")
    void calcularPrecioTotal_T1() {
        carritoService.añadirItem(itemPedido);
        assertEquals(80000, carritoService.calcularPrecioTotal());
    }

    @Test
    @DisplayName("Calcular precio sin items no sea mayor a 0")
    void calcularPrecioTotal_T2() {
        assertFalse(carritoService.calcularPrecioTotal() > 0);
    }

    @Test
    @DisplayName("Calcular precio con item sea mayor a 0")
    void calcularPrecioTotal_T3() {
        carritoService.añadirItem(itemPedido);
        assertTrue(carritoService.calcularPrecioTotal() > 0);
    }

    @Test
    @DisplayName("Calcular precio y que no sea igual al precio de 1 solo item")
    void calcularPrecioTotal_T4() {
        carritoService.añadirItem(itemPedido);
        ItemPedido nuevoItem = new ItemPedido(2L,
                new Camiseta(2L, "PSG", "Ligue 1", 10, "Messi",
                        "2022", 40000, " ", "Messi", "Camiseta",
                        Talla.L, Marca.ADIDAS, TipoCamiseta.EQUIPO),
                Talla.L,
                1);
        carritoService.añadirItem(nuevoItem);
        assertNotEquals(80000, carritoService.calcularPrecioTotal());
    }

    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
    }
}