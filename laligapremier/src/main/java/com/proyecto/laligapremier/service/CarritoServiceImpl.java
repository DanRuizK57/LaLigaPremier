package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.exceptions.SinStockException;
import com.proyecto.laligapremier.models.dao.ICamisetaDao;
import com.proyecto.laligapremier.models.entity.Camiseta;
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
    private ICamisetaService camisetaService;

    private Map<Camiseta, Integer> camisetas = new HashMap<>();

    /**
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     *
     * @param camiseta
     */
    @Override
    public void addProduct(Camiseta camiseta) {
        if (camisetas.containsKey(camiseta)) {
            camisetas.replace(camiseta, camisetas.get(camiseta) + 1);
        } else {
            camisetas.put(camiseta, 1);
        }
    }

    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param camiseta
     */
    @Override
    public void removeProduct(Camiseta camiseta) {
        if (camisetas.containsKey(camiseta)) {
            if (camisetas.get(camiseta) > 1)
                camisetas.replace(camiseta, camisetas.get(camiseta) - 1);
            else if (camisetas.get(camiseta) == 1) {
                camisetas.remove(camiseta);
            }
        }
    }

    /**
     * @return unmodifiable copy of the map
     */
    @Override
    public Map<Camiseta, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(camisetas);
    }

    /**
     * Checkout will rollback if there is not enough of some product in stock
     *
     * @throws SinStockException
     */
    @Override
    public void checkout() throws SinStockException {
        Camiseta camiseta;
        for (Map.Entry<Camiseta, Integer> entry : camisetas.entrySet()) {
            // Refresh quantity for every product before checking
            camiseta = camisetaService.findOne(entry.getKey().getId());
            if (camiseta.getCantidad() < entry.getValue())
                throw new SinStockException(camiseta);
            entry.getKey().setCantidad(camiseta.getCantidad() - entry.getValue());
        }
        camisetaService.save((Camiseta) camisetas.keySet());
        camisetaService.flush();
        camisetas.clear();
    }

    @Override
    public BigDecimal getTotal() {
        return camisetas.entrySet().stream()
                .map(entry -> entry.getKey().getPrecio().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}