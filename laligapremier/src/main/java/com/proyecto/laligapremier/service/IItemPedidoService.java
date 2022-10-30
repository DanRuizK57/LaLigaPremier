package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.entity.ItemPedido;

import java.util.List;

public interface IItemPedidoService {

    List<ItemPedido> findAll();
    void delete(Long id);
    ItemPedido findOne(Long id);
    void save(ItemPedido item);
    void deleteAll();
    void flush();

    void agregarAlCarrito(ItemPedido item);

}
