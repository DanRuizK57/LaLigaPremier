package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.entity.ItemPedido;
import com.proyecto.laligapremier.models.enums.Talla;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemPedidoServiceImplTest {

    @Autowired
    private ItemPedidoServiceImpl itemPedidoService;

    @Autowired
    private CamisetaServiceImpl camisetaService;

    private ItemPedido itemPedido;

    private List<ItemPedido> listaPedidoItem;

    private  ItemPedido itemPedido2;

    @BeforeEach
    @Transactional
    void inicioPrueba(TestInfo testInfo) {
        System.out.println("**** Iniciando " + testInfo.getDisplayName() + " ****");
        listaPedidoItem = itemPedidoService.findAll();
        var camiseta = camisetaService.findOne(1L);
        itemPedido = new ItemPedido();
        itemPedido.setCantidad(4);
        itemPedido.setCamiseta(camiseta);
        itemPedido.setId(1L);
        itemPedido.setTalla(camiseta.getTalla());
        itemPedidoService.save(itemPedido);

        var camiseta2 = camisetaService.findOne(2L);
        itemPedido2 = new ItemPedido();
        itemPedido2.setCantidad(4);
        itemPedido2.setCamiseta(camiseta2);
        itemPedido2.setId(2L);
        itemPedido2.setTalla(camiseta2.getTalla());
        itemPedidoService.save(itemPedido2);

    }

    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
        listaPedidoItem = null;
    }


    @Test
    @DisplayName("Trae una lista correctamente")
    void findAll_T1() {
        assertNotNull(listaPedidoItem);
    }

    @Test
    @DisplayName("La lista de ItemPedido no esta vacia")
    void findAll_T2() {
        assertTrue(listaPedidoItem.size() > 0);
    }

    @Test
    @DisplayName("lista obtenida esta vacia ")
    void findAll_T3() {
        assertFalse(listaPedidoItem.isEmpty());
    }

    @Test
    void delete_T1() {
        assertThrows(EmptyResultDataAccessException.class, () -> itemPedidoService.delete(1000L));
    }
    @Test
    @Transactional
    void delete_T2() {
        var camiseta2 = camisetaService.findOne(2L);
        itemPedido2 = new ItemPedido();
        itemPedido2.setCantidad(4);
        itemPedido2.setCamiseta(camiseta2);
        itemPedido2.setId(401L);
        itemPedido2.setTalla(camiseta2.getTalla());
        itemPedidoService.save(itemPedido2);
        itemPedidoService.delete(400L);
        assertNull(itemPedido2);
    }

    @Test
    void findOne_T1() {
        var itemPedido = itemPedidoService.findOne(1L);
        assertNotNull(itemPedido);
    }

    @Test
    void findOne_T2() {
        var itemPedido = itemPedidoService.findOne(1L);
        assertTrue(itemPedido.getId().equals(1L));
    }

    @Test
    void findOne_T3() {

        assertThrows(EmptyResultDataAccessException.class, ()-> itemPedidoService.findOne(210L) );

    }



    @Test
    void save() {
    }

    @Test
    void agregarAlCarrito() {
        itemPedidoService.agregarAlCarrito(itemPedido);

    }


}