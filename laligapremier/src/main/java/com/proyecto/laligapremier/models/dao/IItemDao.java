package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IItemDao extends PagingAndSortingRepository<Item, Long> {
}
