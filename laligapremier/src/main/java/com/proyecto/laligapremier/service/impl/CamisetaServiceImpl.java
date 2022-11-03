package com.proyecto.laligapremier.service.impl;
import com.proyecto.laligapremier.models.dao.ICamisetaDao;
import com.proyecto.laligapremier.models.entity.Camiseta;
import com.proyecto.laligapremier.models.entity.Filtro;
import com.proyecto.laligapremier.models.enums.TipoCamiseta;
import com.proyecto.laligapremier.service.ICamisetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CamisetaServiceImpl implements ICamisetaService {

    @Autowired
    private ICamisetaDao camisetaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Camiseta> findAll() {
        return (List<Camiseta>) camisetaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Camiseta> findAll(Pageable pageable) {
        return camisetaDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        camisetaDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Camiseta findOne(Long id) {
        return camisetaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Camiseta camiseta) {
        camisetaDao.save(camiseta);
    }

    @Override
    public void flush() {
        camisetaDao.flush();
    }


    @Override
    @Transactional()
    public List<Camiseta> findByNombre(String q) {
        List<Camiseta> camisetas = camisetaDao.findByNombre(q);
        return camisetas;
    }

    @Override
    public Page<Camiseta> findByNombre(String q, Pageable pageable) {
        return camisetaDao.findByNombre(q, pageable);
    }

    @Override
    public Optional<Camiseta> findById(Long id) {
        return camisetaDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Camiseta> listarPorFiltros(Filtro objetoFiltro) {

        List<Camiseta> camisetasFiltradas = camisetaDao.listarPorMarcaYPrecio(objetoFiltro.getMarca(), Integer.parseInt(objetoFiltro.getPrecio().getPrecio()));

        return camisetasFiltradas;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Camiseta> listarPorFiltros(Filtro objetoFiltro, Pageable pageable) {

        List<Camiseta> camisetasFiltradas = camisetaDao.listarPorMarcaYPrecio(objetoFiltro.getMarca(), Integer.parseInt(objetoFiltro.getPrecio().getPrecio()));

        Page<Camiseta> camisetasFiltradasPage = new PageImpl<>(camisetasFiltradas);

        return camisetasFiltradasPage;
    }

    @Override
    public List<Camiseta> listarPorTipo(TipoCamiseta tipoCamiseta) {
        List<Camiseta> camisetas = camisetaDao.listarPorTipo(tipoCamiseta);
        return camisetas;
    }

    @Override
    public Page<Camiseta> listarPorTipo(TipoCamiseta tipoCamiseta, Pageable pageable) {
        List<Camiseta> camisetas = camisetaDao.listarPorTipo(tipoCamiseta);
        Page<Camiseta> camisetasPorTipoPage = new PageImpl<>(camisetas);
        return camisetasPorTipoPage;
    }
}