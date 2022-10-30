package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.entity.ItemPedido;

import java.util.List;

public interface ICarritoService {

    void añadirItem(ItemPedido item);

    void eliminarItem(Long itemId);

    List<ItemPedido> obtenerItemsDelCarrito();

    void sumarCantidad(Long itemId);

    void restarCantidad(Long itemId);

    Integer contadorItems();

    Integer calcularPrecioTotal();

    void reiniciarCarrito();

}
