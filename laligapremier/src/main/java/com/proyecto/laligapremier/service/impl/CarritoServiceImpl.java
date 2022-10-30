package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.entity.ItemPedido;
import com.proyecto.laligapremier.service.ICarritoService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class CarritoServiceImpl implements ICarritoService {

    private List<ItemPedido> items = new ArrayList<>();

    /**
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     *
     * @param item
     */
    @Override
    public void añadirItem(ItemPedido item) {
        items.add(item);
    }

    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param itemId
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
     * @return unmodifiable copy of the map
     */
    @Override
    public List<ItemPedido> obtenerItemsDelCarrito() {
        return items;
    }

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

    @Override
    public Integer contadorItems() {
        Integer contador = 0;

        for (int i = 0; i < items.size(); i++) {
            contador += items.get(i).getCantidad();
        }
        return contador;
    }

    @Override
    public Integer calcularPrecioTotal() {
        Integer precioTotal = 0;
        for (int i = 0; i < items.size(); i++) {
            precioTotal += items.get(i).calcularPrecioItem();
        }
        return precioTotal;
    }

    @Override
    public void reiniciarCarrito() {
        items.clear();
    }

}