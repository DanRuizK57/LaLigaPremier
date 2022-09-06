package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.Administrador;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IAdministradorDao extends PagingAndSortingRepository<Administrador , Long> {
}
