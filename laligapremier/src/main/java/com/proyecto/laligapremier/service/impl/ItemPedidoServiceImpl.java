package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.dao.IItemPedidoDao;
import com.proyecto.laligapremier.models.entity.ItemPedido;
import com.proyecto.laligapremier.service.ICarritoService;
import com.proyecto.laligapremier.service.IItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la clase de IItemPedidoService, donde se dan lógica a sus métodos.
 */

@Service
public class ItemPedidoServiceImpl implements IItemPedidoService {

    /**
     * Inyección de dependencias para acceder a sus métodos y así realizar consultas a la base de datos u ocupar
     * métodos de otro servicio.
     */

    @Autowired
    private IItemPedidoDao itemPedidoDao;

    @Autowired
    private ICarritoService carritoService;

    /**
     * Método que guarda en una lista todos los objetos del tipo ItemPedido disponibles en la base de datos.
     * @return Lista de objetos del tipo ItemPedido.
     */
    @Override
    public List<ItemPedido> findAll() {
        return (List<ItemPedido>) itemPedidoDao.findAll();
    }

    /**
     * Método que elimina un objeto del tipo ItemPedido según su id en la base de datos.
     * @param id Objeto de tipo Long, usado para elegir el item a eliminar.
     */
    @Override
    public void delete(Long id) {
        itemPedidoDao.deleteById(id);
    }

    /**
     * Método que busca un objeto del tipo ItemPedido según su id en la base de datos.
     * @param id Objeto de tipo Long, usado para encontrar el item seleccionado.
     * @return Objeto del tipo ItemPedido.
     */
    @Override
    public ItemPedido findOne(Long id) {
        return itemPedidoDao.findById(id).orElse(null);
    }

    /**
     * Método que guarda un objeto del tipo ItemPedido en la base de datos.
     * @param item Objeto de tipo ItemPedido, usado para guardarlo en la base de datos.
     */
    @Override
    public void save(ItemPedido item) {
        itemPedidoDao.save(item);
    }

    /**
     * Método que elimina todos los objetos del tipo ItemPedido en la base de datos.
     */
    @Override
    public void deleteAll() {
        itemPedidoDao.deleteAll(findAll());
    }

    /**
     * Método que refresca el flujo de datos.
     */
    @Override
    public void flush() {
        itemPedidoDao.flush();
    }

    /**
     * Método que guarda un objeto del tipo ItemPedido en un carrito.
     * @param item Objeto de tipo ItemPedido, usado para guardarlo en un carrito.
     */
    @Override
    public void agregarAlCarrito(ItemPedido item) {
        carritoService.añadirItem(item);
    }

}
