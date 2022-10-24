package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.exceptions.SinStockException;
import com.proyecto.laligapremier.models.entity.ItemPedido;
import com.proyecto.laligapremier.service.ICarritoService;
import com.proyecto.laligapremier.service.IItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class CarritoServiceImpl implements ICarritoService {

    @Autowired
    private IItemPedidoService itemPedidoService;

    private Map<ItemPedido, Integer> items = new HashMap<>();

    /**
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     *
     * @param item
     */
    @Override
    public void addProduct(ItemPedido item) {
        if (items.containsKey(item)) {
            items.replace(item, items.get(item) + 1);
        } else {
            items.put(item, 1);
        }
    }

    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param item
     */
    @Override
    public void removeProduct(ItemPedido item) {
        if (items.containsKey(item)) {
            if (items.get(item) > 1)
                items.replace(item, items.get(item) - 1);
            else if (items.get(item) == 1) {
                items.remove(item);
            }
        }
    }

    /**
     * @return unmodifiable copy of the map
     */
    @Override
    public Map<ItemPedido, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(items);
    }

    /**
     * Checkout will rollback if there is not enough of some product in stock
     *
     * @throws SinStockException
     */
    @Override
    public void checkout() throws SinStockException {
        ItemPedido item;
        for (Map.Entry<ItemPedido, Integer> entry : items.entrySet()) {
            // Refresh quantity for every product before checking
            item = itemPedidoService.findOne(entry.getKey().getId());
            if (item.getCantidad() < entry.getValue())
                throw new SinStockException(item);
            entry.getKey().setCantidad(item.getCantidad() - entry.getValue());
        }
        itemPedidoService.save((ItemPedido) items.keySet());
        itemPedidoService.flush();
        items.clear();
    }

    @Override
    public BigDecimal getTotal() {
        return items.entrySet().stream()
                .map(entry -> entry.getKey().getCamiseta().getPrecio().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}