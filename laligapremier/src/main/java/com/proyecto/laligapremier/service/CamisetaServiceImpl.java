package com.proyecto.laligapremier.service;
import com.proyecto.laligapremier.models.dao.ICamisetaDao;
import com.proyecto.laligapremier.models.entity.Camiseta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    @Transactional(readOnly = true)
    public List<Camiseta> listarEquipos() {
        return findAll().stream()
                .filter(p -> p.getTipoCamiseta()
                        .getTipo()
                        .equalsIgnoreCase("equipo"))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Camiseta> listarEquipos(Pageable pageable) {
        List<Camiseta> equipos = findAll(pageable).stream()
                .filter(p -> p.getTipoCamiseta()
                        .getTipo()
                        .equalsIgnoreCase("equipo")).toList();
        Page<Camiseta> equiposPage = new PageImpl<>(equipos);

        return equiposPage;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Camiseta> listarSelecciones() {
        return findAll().stream()
                .filter(p -> p.getTipoCamiseta()
                        .getTipo()
                        .equalsIgnoreCase("seleccion"))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Camiseta> listarSelecciones(Pageable pageable) {
        List<Camiseta> selecciones = findAll(pageable).stream()
                .filter(p -> p.getTipoCamiseta()
                        .getTipo()
                        .equalsIgnoreCase("seleccion")).toList();
        Page<Camiseta> seleccionesPage = new PageImpl<>(selecciones);

        return seleccionesPage;
    }
}