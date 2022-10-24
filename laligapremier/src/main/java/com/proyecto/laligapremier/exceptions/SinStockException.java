package com.proyecto.laligapremier.exceptions;

import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.ItemPedido;

public class SinStockException extends Exception {

    private static final String DEFAULT_MESSAGE = "Not enough products in stock";

    public SinStockException() {
        super(DEFAULT_MESSAGE);
    }

    public SinStockException(ItemPedido item) {
        super(String.format("Not enough %s products in stock. Only %d left", item.getCamiseta().getNombre(), item.getCamiseta().getCantidad()));
    }

}
