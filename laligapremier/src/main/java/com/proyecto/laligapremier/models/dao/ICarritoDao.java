package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.Carrito;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICarritoDao extends PagingAndSortingRepository<Carrito , Long> {
    

}
