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
    @DisplayName("lista cambia de tamaño")
    void findAll_T4(){
        var tamaño = itemPedidoService.findAll().size();
        var itemPedido4 = new ItemPedido();
        var camiseta = camisetaService.findOne(5L);
        itemPedido4 = new ItemPedido();
        itemPedido4.setCantidad(4);
        itemPedido4.setCamiseta(camiseta);
        itemPedido4.setId(1L);
        itemPedido4.setTalla(camiseta.getTalla());
        itemPedidoService.save(itemPedido4);

        var tamañoFinal = itemPedidoService.findAll().size();

        assertNotEquals(tamañoFinal,tamaño);


    }


    @Test
    @DisplayName("Test guardar ItemPedido")
    void save() {
        var camiseta3 = camisetaService.findOne(3L);
        var itemPedidoGuardar = new ItemPedido();
        assertAll(
                () -> {
                    itemPedidoGuardar.setCantidad(4);
                    itemPedidoGuardar.setCamiseta(camiseta3);
                    itemPedidoGuardar.setId(5L);
                    itemPedidoGuardar.setTalla(camiseta3.getTalla());
                    itemPedidoService.save(itemPedidoGuardar);

                    itemPedidoService.findAll().stream().forEach(p -> System.out.println(p.getId()));

                    assertNotNull(itemPedidoService.findOne(3L));
                },
                ()->{
                    assertNull(itemPedidoService.findOne(200L));
                },
                ()->{
                    assertNotEquals(itemPedidoService.findOne(2L), itemPedidoService.findOne(3L));
                },
                ()-> {

                    itemPedidoGuardar.setCantidad(4);
                    itemPedidoGuardar.setCamiseta(camiseta3);
                    itemPedidoGuardar.setId(5L);
                    itemPedidoGuardar.setTalla(camiseta3.getTalla());
                    itemPedidoService.save(itemPedidoGuardar);

                    itemPedidoService.findAll().stream().forEach(p -> System.out.println(p.getId()));

                    assertTrue(itemPedidoService.findOne(4L).getId().equals(4L));

                }

        );
    }



}