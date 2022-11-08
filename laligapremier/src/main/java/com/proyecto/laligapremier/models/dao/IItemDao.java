package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Clase que establece conexión directa con la base de datos e interactúa con la entidad Item.
 */
public interface IItemDao extends PagingAndSortingRepository<Item, Long> {
}
