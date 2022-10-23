package com.proyecto.laligapremier.exceptions;

import com.proyecto.laligapremier.models.entity.Camiseta;

public class SinStockException extends Exception {

    private static final String DEFAULT_MESSAGE = "Not enough products in stock";

    public SinStockException() {
        super(DEFAULT_MESSAGE);
    }

    public SinStockException(Camiseta camiseta) {
        super(String.format("Not enough %s products in stock. Only %d left", camiseta.getNombre(), camiseta.getCantidad()));
    }

}
