package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.ItemPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IItemPedidoService {

    List<ItemPedido> findAll();
    void delete(Long id);
    ItemPedido findOne(Long id);
    void save(ItemPedido item);
    void flush();

}
