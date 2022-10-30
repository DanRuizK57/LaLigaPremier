package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.entity.Item;

import java.util.List;

public interface IItemService {

    void save(Item item);

    List<Item> listar();

    void delete();

}
