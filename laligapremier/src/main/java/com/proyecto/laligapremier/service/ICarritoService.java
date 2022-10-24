package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.exceptions.SinStockException;
import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.ItemPedido;

import java.math.BigDecimal;
import java.util.Map;

public interface ICarritoService {

    void addProduct(ItemPedido item);

    void removeProduct(ItemPedido item);

    Map<ItemPedido, Integer> getProductsInCart();

    void checkout() throws SinStockException;

    BigDecimal getTotal();

}
