package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {
}
