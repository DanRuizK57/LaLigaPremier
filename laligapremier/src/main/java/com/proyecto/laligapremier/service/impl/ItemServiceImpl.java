package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.dao.IItemDao;
import com.proyecto.laligapremier.models.entity.Item;
import com.proyecto.laligapremier.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la clase de IItemService, donde se dan lógica a sus métodos.
 */

@Service
public class ItemServiceImpl implements IItemService {

    /**
     * Inyección de la clase IItemDao para acceder a sus métodos y así realizar consultas a la base de datos.
     */

    @Autowired
    private IItemDao itemDao;

    /**
     * Método que guarda un objeto del tipo Item en la base de datos.
     * @param item Objeto de tipo Item, usado para guardarlo en la base de datos.
     */
    @Override
    public void save(Item item) {
        itemDao.save(item);
    }

    /**
     * Método que guarda en una lista todos los objetos del tipo Item disponibles en la base de datos.
     * @return Lista de objetos del tipo Item.
     */
    @Override
    public List<Item> listar() {
        return (List<Item>) itemDao.findAll();
    }

}
