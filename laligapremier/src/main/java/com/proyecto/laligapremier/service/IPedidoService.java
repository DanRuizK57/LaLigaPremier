package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPedidoService {

    List<Pedido> findAll();
    Page<Pedido> findAll(Pageable pageable);
    void delete(Long id);
    Pedido findOne(Long id);
    void save(Pedido pedido);

}
