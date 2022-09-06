package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.Camiseta;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICamisetaDao extends PagingAndSortingRepository<Camiseta, Long> {
}
