package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.Camiseta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICamisetaDao extends JpaRepository<Camiseta, Long> {

    @Query(value = "SELECT * FROM camisetas WHERE camisetas.nombre LIKE %:q%", nativeQuery = true)
    public List<Camiseta> findByNombre(@Param("q") String q);

    @Query(value = "SELECT * FROM camisetas WHERE camisetas.nombre LIKE %:q%", nativeQuery = true)
    public Page<Camiseta> findByNombre(@Param("q")String q, Pageable pageable);

}
