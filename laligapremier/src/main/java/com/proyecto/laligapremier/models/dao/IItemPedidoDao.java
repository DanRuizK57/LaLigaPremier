package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemPedidoDao extends JpaRepository<ItemPedido, Long> {
}
