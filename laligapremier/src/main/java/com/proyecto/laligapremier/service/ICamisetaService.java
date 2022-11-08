package com.proyecto.laligapremier.service;
import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.Filtro;
import com.proyecto.laligapremier.models.enums.TipoCamiseta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de la clase Camiseta donde se establecen sus métodos para luego ser implementados
 * en la clase CamisetaServiceImpl.
 */

public interface ICamisetaService {
    List<Camiseta> findAll();
    Page<Camiseta> findAll(Pageable pageable);
    void delete(Long id);
    Camiseta findOne(Long id);
    void save(Camiseta camiseta);

    void flush();

    List<Camiseta> findByNombre(String term);

    Page<Camiseta> findByNombre(String term, Pageable pageable);

    Optional<Camiseta> findById(Long id);

    List<Camiseta> listarPorFiltros(Filtro objetoFiltro);

    Page<Camiseta> listarPorFiltros(Filtro objetoFiltro, Pageable pageable);

    List<Camiseta> listarPorTipo(TipoCamiseta tipoCamiseta);

    Page<Camiseta> listarPorTipo(TipoCamiseta tipoCamiseta, Pageable pageable);

}
