package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.Camiseta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ICamisetaDao extends PagingAndSortingRepository<Camiseta, Long> {
}
