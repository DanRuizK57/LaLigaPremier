package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.ItemPedido;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IItemPedido extends PagingAndSortingRepository<ItemPedido, Long> {
}
