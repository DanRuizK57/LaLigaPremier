package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interfaz de la clase Pedido donde se establecen sus métodos para luego ser implementados
 * en la clase PedidoServiceImpl.
 */

public interface IPedidoService {

    List<Pedido> findAll();
    Page<Pedido> findAll(Pageable pageable);
    void delete(Long id);
    Pedido findOne(Long id);
    void save(Pedido pedido);

}
