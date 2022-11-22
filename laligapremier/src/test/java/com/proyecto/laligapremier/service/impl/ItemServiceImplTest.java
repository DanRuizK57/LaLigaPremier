package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.entity.Item;
import com.proyecto.laligapremier.models.enums.Talla;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceImplTest {

    @Autowired
    private ItemServiceImpl itemService;

    private Item itemPrueba;
    private List<Item> lista;

    @BeforeEach
    void inicioPrueba(TestInfo testInfo) {
        System.out.println("**** Iniciando " + testInfo.getDisplayName() + " ****");
        lista = itemService.listar();
        itemPrueba = new Item();
    }


    @Test
    @DisplayName("Item se guarda correctamente")
    void save_T1() {
        itemPrueba.setId(1L);
        itemService.save(itemPrueba);
        var lista = itemService.listar();
        var identidad = new Item();
        var itemBBDD =
                lista.stream()
                        .filter(p -> p.getId().equals(1L))
                        .reduce(identidad, (acumulador, combinador) -> combinador);
        assertAll(
                () -> assertNotNull(itemBBDD),
                () -> assertEquals(itemBBDD.getId(), itemPrueba.getId()),
                () -> assertNotEquals(itemBBDD, itemPrueba),
                () -> assertNull(itemBBDD.getNombre()));
    }

    @Test
    void listar() {

        assertAll(
                () -> assertNotNull(lista),
                () -> assertFalse(lista.isEmpty()),
                () -> assertTrue(lista.size() > 1)
        );
    }

    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
    }
}