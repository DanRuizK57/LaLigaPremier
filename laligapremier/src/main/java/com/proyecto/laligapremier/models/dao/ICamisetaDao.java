package com.proyecto.laligapremier.models.dao;

import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.TipoCamiseta;
import com.proyecto.laligapremier.models.enums.TipoPrecio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICamisetaDao extends JpaRepository<Camiseta, Long> {

    @Query(value = "SELECT * FROM camisetas WHERE camisetas.nombre LIKE %:q%", nativeQuery = true)
    public List<Camiseta> findByNombre(@Param("q") String q);

    @Query(value = "SELECT * FROM camisetas WHERE camisetas.nombre LIKE %:q%", nativeQuery = true)
    public Page<Camiseta> findByNombre(@Param("q")String q, Pageable pageable);

    @Query(value = "SELECT * FROM camisetas WHERE camisetas.tipo_camiseta LIKE %:tipoCamiseta%", nativeQuery = true)
    List<Camiseta> listarPorTipo(TipoCamiseta tipoCamiseta);

    @Query(value = "SELECT * FROM camisetas WHERE camisetas.tipo_camiseta LIKE %:tipoCamiseta%", nativeQuery = true)
    Page<Camiseta> listarPorTipo(TipoCamiseta tipoCamiseta, Pageable pageable);

    @Query(value = "SELECT * FROM camisetas WHERE camisetas.marca LIKE %:marca%", nativeQuery = true)
    List<Camiseta> listarPorMarca(Marca marca);

    @Query(value = "SELECT * FROM camisetas WHERE camisetas.marca LIKE %:marca%", nativeQuery = true)
    Page<Camiseta> listarPorMarca(Marca marca, Pageable pageable);

    @Query(value = "SELECT * FROM camisetas WHERE camisetas.precio <= %:precio%", nativeQuery = true)
    List<Camiseta> listarPorPrecio(Integer precio);

    @Query(value = "SELECT * FROM camisetas WHERE camisetas.precio <= %:precio%", nativeQuery = true)
    Page<Camiseta> listarPorPrecio(Integer precio, Pageable pageable);

    @Query(value = "SELECT * FROM camisetas WHERE camisetas.marca LIKE %:marca% AND camisetas.precio <=:precio", nativeQuery = true)
    List<Camiseta> listarPorMarcaYPrecio(Marca marca, Integer precio);

    @Query(value = "SELECT * FROM camisetas WHERE camisetas.marca LIKE %:marca% AND camisetas.precio <=:precio", nativeQuery = true)
    Page<Camiseta> listarPorMarcaYPrecio(Marca marca, Integer precio, Pageable pageable);


}
