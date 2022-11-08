package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Clase que establece conexión directa con la base de datos e interactúa con la entidad ItemPedido.
 */
public interface IItemPedidoDao extends JpaRepository<ItemPedido, Long> {
}
