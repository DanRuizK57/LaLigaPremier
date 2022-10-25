package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.exceptions.SinStockException;
import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.ItemPedido;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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
