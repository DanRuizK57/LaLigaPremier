package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.exceptions.SinStockException;
import com.proyecto.laligapremier.models.entity.Camiseta;

import java.math.BigDecimal;
import java.util.Map;

public interface ICarritoService {

    void addProduct(Camiseta camiseta);

    void removeProduct(Camiseta camiseta);

    Map<Camiseta, Integer> getProductsInCart();

    void checkout() throws SinStockException;

    BigDecimal getTotal();

}
