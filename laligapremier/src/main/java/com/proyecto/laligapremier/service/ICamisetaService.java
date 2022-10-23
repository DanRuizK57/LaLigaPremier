package com.proyecto.laligapremier.service;
import com.proyecto.laligapremier.models.entity.Camiseta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICamisetaService {
    List<Camiseta> findAll();
    Page<Camiseta> findAll(Pageable pageable);
    void delete(Long id);
    Camiseta findOne(Long id);
    void save(Camiseta camiseta);

    void flush();

    List<Camiseta> listarEquipos();

    Page<Camiseta> listarEquipos(Pageable pageable);

    List<Camiseta> listarSelecciones();

    Page<Camiseta> listarSelecciones(Pageable pageable);

    public List<Camiseta> findByNombre(String term);

    Page<Camiseta> findByNombre(String term, Pageable pageable);

    Optional<Camiseta> findById(Long id);


}
