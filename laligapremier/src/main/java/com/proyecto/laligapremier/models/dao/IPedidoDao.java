package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.Pedido;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IPedidoDao extends PagingAndSortingRepository<Pedido, Long> {
}
