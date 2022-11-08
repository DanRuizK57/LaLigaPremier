package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.Pedido;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Clase que establece conexión directa con la base de datos e interactúa con la entidad Pedido.
 */
public interface IPedidoDao extends PagingAndSortingRepository<Pedido, Long> {
}
