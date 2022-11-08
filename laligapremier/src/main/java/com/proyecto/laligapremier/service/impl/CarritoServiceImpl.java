package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.entity.ItemPedido;
import com.proyecto.laligapremier.service.ICarritoService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

/**
 * Implementación de la clase de ICarritoService, donde se dan lógica a sus métodos.
 */

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class CarritoServiceImpl implements ICarritoService {

    /**
     * Se crea una lista del objeto ItemPedido para almacenarlos temporalmente y trabajar con ellos.
     */
    private List<ItemPedido> items = new ArrayList<>();

    /**
     * Método que añade a la lista objetos del tipo ItemPedido.
     * @param item Objeto del tipo ItemPedido, usado para guardarlo en la lista.
     */
    @Override
    public void añadirItem(ItemPedido item) {
        items.add(item);
    }

    /**
     * Método que elimina un objeto del tipo ItemPedido de la lista según su id.
     * @param itemId Objeto del tipo Long, usado para encontrar el item en la lista y eliminarlo.
     */
    @Override
    public void eliminarItem(Long itemId) {
        ItemPedido item = new ItemPedido();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(itemId)) {
                item = items.get(i);
            }
        }
        items.remove(item);
    }

    /**
     * Método que obtiene la lista objetos del tipo ItemPedido.
     * @return Lista de objetos del tipo ItemPedido.
     */
    @Override
    public List<ItemPedido> obtenerItemsDelCarrito() {
        return items;
    }

    /**
     * Método que suma en 1 la cantidad de un item seleccionado.
     * @param itemId Objeto del tipo Long, usado para encontrar el item en la lista.
     */
    @Override
    public void sumarCantidad(Long itemId) {
        ItemPedido item = new ItemPedido();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(itemId)) {
                item = items.get(i);
            }
        }
        Integer cantidad = item.getCantidad();
        item.setCantidad(cantidad + 1);
    }

    /**
     * Método que resta en 1 la cantidad de un item seleccionado.
     * @param itemId Objeto del tipo Long, usado para encontrar el item en la lista.
     */
    @Override
    public void restarCantidad(Long itemId) {
        ItemPedido item = new ItemPedido();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(itemId)) {
                item = items.get(i);
            }
        }

        Integer cantidad = item.getCantidad();

        if (cantidad > 0) {
            item.setCantidad(cantidad - 1);
        }
    }

    /**
     * Método que cuenta la cantidad de items de la lista.
     * @return Número de items en la lista.
     */
    @Override
    public Integer contadorItems() {
        Integer contador = 0;

        for (int i = 0; i < items.size(); i++) {
            contador += items.get(i).getCantidad();
        }
        return contador;
    }

    /**
     * Método que suma todos los precios totales de los items en la lista, es decir, calcula el precio total de
     * cada item (precio x cantidad) y los suma.
     * @return Precio total de la lista.
     */
    @Override
    public Integer calcularPrecioTotal() {
        Integer precioTotal = 0;
        for (int i = 0; i < items.size(); i++) {
            precioTotal += items.get(i).calcularPrecioItem();
        }
        return precioTotal;
    }

    /**
     * Método que elimina todos los items de la lista.
     */
    @Override
    public void reiniciarCarrito() {
        items.clear();
    }

}