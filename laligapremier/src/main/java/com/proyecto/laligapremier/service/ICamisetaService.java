package com.proyecto.laligapremier.service;
import com.proyecto.laligapremier.models.entity.Camiseta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICamisetaService {
    List<Camiseta> findAll();
    Page<Camiseta> findAll(Pageable pageable);
    void delete(Long id);
    Camiseta findOne(Long id);
    void save(Camiseta camiseta);


}
