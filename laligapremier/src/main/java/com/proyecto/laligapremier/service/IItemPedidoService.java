package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.entity.ItemPedido;

import java.util.List;

/**
 * Interfaz de la clase ItemPedido donde se establecen sus métodos para luego ser implementados
 * en la clase ItemPedidoServiceImpl.
 */

public interface IItemPedidoService {

    List<ItemPedido> findAll();
    void delete(Long id);
    ItemPedido findOne(Long id);
    void save(ItemPedido item);
    void deleteAll();
    void flush();

    void agregarAlCarrito(ItemPedido item);

}
