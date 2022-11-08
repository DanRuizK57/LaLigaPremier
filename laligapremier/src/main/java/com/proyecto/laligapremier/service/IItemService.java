package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.entity.Item;

import java.util.List;

/**
 * Interfaz de la clase Item donde se establecen sus métodos para luego ser implementados
 * en la clase ItemServiceImpl.
 */

public interface IItemService {

    void save(Item item);

    List<Item> listar();

}
