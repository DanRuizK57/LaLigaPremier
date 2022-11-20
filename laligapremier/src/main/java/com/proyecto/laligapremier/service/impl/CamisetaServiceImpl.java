package com.proyecto.laligapremier.service.impl;
import com.proyecto.laligapremier.exceptions.CamisetaNoEncontradaException;
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

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la clase de ICamisetaService, donde se dan lógica a sus métodos.
 */

@Service
public class CamisetaServiceImpl implements ICamisetaService {

    /**
     * Inyección de la clase ICamisetaDao para acceder a sus métodos y así realizar consultas a la base de datos.
     */
    @Autowired
    private ICamisetaDao camisetaDao;

    /**
     * Método que guarda en una lista todos los objetos del tipo Camiseta disponibles en la base de datos.
     * @return Lista de objetos del tipo Camiseta.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Camiseta> findAll() {
        return (List<Camiseta>) camisetaDao.findAll();
    }

    /**
     * Método que guarda en una lista todos los objetos del tipo Camiseta disponibles en la base de datos,
     * pero los distribuye según el número de elementos indicados en el paginador.
     * @param pageable Objeto del paginador, usado para establecer el número de camisetas que se muestran por página.
     * @return Lista paginada de objetos del tipo Camiseta.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Camiseta> findAll(Pageable pageable) {
        return camisetaDao.findAll(pageable);
    }

    /**
     * Método que elimina un objeto del tipo Camiseta según su id en la base de datos.
     * @param id Objeto de tipo Long, usado para elegir a la camiseta a eliminar.
     */
    @Override
    @Transactional
    public void delete(Long id) throws CamisetaNoEncontradaException {
        camisetaDao.deleteById(id);
    }

    /**
     * Método que busca un objeto del tipo Camiseta según su id en la base de datos.
     * @param id Objeto de tipo Long, usado para encontrar la camiseta seleccionada.
     * @return Objeto del tipo Camiseta.
     */
    @Override
    @Transactional(readOnly = true)
    public Camiseta findOne(Long id) {
        return camisetaDao.findById(id).orElse(null);
    }

    /**
     * Método que guarda un objeto del tipo Camiseta en la base de datos.
     * @param camiseta Objeto de tipo Camiseta, usado para guardarlo en la base de datos.
     */
    @Override
    @Transactional
    public void save(Camiseta camiseta) {
        camisetaDao.save(camiseta);
    }

    /**
     * Método que refresca el flujo de datos.
     */
    @Override
    public void flush() {
        camisetaDao.flush();
    }


    /**
     * Método que busca objetos del tipo Camiseta en la base de datos con un nombre específico.
     * @param q Objeto de tipo String, usado para buscar una lista de camisetas con ese nombre en la base de datos.
     * @return Lista de objetos del tipo Camiseta.
     */
    @Override
    @Transactional()
    public List<Camiseta> findByNombre(String q) {
        List<Camiseta> camisetas = camisetaDao.findByNombre(q);
        return camisetas;
    }

    /**
     * Método que busca objetos del tipo Camiseta en la base de datos con un nombre específico,
     * pero los distribuye según el número de elementos indicados en el paginador.
     * @param q Objeto de tipo String, usado para buscar una lista de camisetas con ese nombre en la base de datos.
     * @param pageable Objeto del paginador, usado para establecer el número de camisetas que se muestran por página.
     * @return Lista paginada de objetos del tipo Camiseta.
     */
    @Override
    public Page<Camiseta> findByNombre(String q, Pageable pageable) {
        return camisetaDao.findByNombre(q, pageable);
    }

    /**
     * Método que busca un objeto del tipo Camiseta según su id en la base de datos.
     * @param id Objeto de tipo Long, usado para encontrar la camiseta seleccionada.
     * @return Objeto del tipo Camiseta.
     */
    @Override
    public Optional<Camiseta> findById(Long id) {
        return camisetaDao.findById(id);
    }

    /**
     * Método que busca en la base de datos y lista de objetos del tipo Camiseta según los filtros que se le coloquen.
     * @param objetoFiltro Objeto de tipo Filtro, usado para obtener los parámetros por los cuales filtrar las
     *                     camisetas, como por su marca o rango de precios.
     * @return Lista de objetos del tipo Camiseta.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Camiseta> listarPorFiltros(Filtro objetoFiltro) {

        List<Camiseta> camisetasFiltradas = camisetaDao.listarPorMarcaYPrecio(objetoFiltro.getMarca(), Integer.parseInt(objetoFiltro.getPrecio().getPrecio()));

        return camisetasFiltradas;
    }

    /**
     * Método que busca en la base de datos y lista de objetos del tipo Camiseta según los filtros que se le coloquen,
     * pero los distribuye según el número de elementos indicados en el paginador.
     * @param objetoFiltro Objeto de tipo Filtro, usado para obtener los parámetros por los cuales filtrar las
     *                     camisetas, como por su marca o rango de precios.
     * @param pageable Objeto del paginador, usado para establecer el número de camisetas que se muestran por página.
     * @return Lista paginada de objetos del tipo Camiseta.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Camiseta> listarPorFiltros(Filtro objetoFiltro, Pageable pageable) {

        List<Camiseta> camisetasFiltradas = camisetaDao.listarPorMarcaYPrecio(objetoFiltro.getMarca(), Integer.parseInt(objetoFiltro.getPrecio().getPrecio()));

        Page<Camiseta> camisetasFiltradasPage = new PageImpl<>(camisetasFiltradas);

        return camisetasFiltradasPage;
    }

    /**
     * Método que busca en la base de datos y lista de objetos del tipo Camiseta según el tipo de camiseta que se
     * seleccione, pero los distribuye según el número de elementos indicados en el paginador.
     * @param tipoCamiseta Objeto de tipo TipoCamiseta, usado para obtener los parámetros del enum, es decir,
     *                     para obtener si la camiseta es de Equipo o de Selección.
     * @return Lista de objetos del tipo Camiseta.
     */
    @Override
    public List<Camiseta> listarPorTipo(TipoCamiseta tipoCamiseta) {
        List<Camiseta> camisetas = camisetaDao.listarPorTipo(tipoCamiseta);
        return camisetas;
    }

    /**
     * Método que busca en la base de datos y lista de objetos del tipo Camiseta según el tipo de camiseta que se
     * seleccione.
     * @param tipoCamiseta Objeto de tipo TipoCamiseta, usado para obtener los parámetros del enum, es decir,
     *                     para obtener si la camiseta es de Equipo o de Selección.
     * @param pageable Objeto del paginador, usado para establecer el número de camisetas que se muestran por página.
     * @return Lista de objetos del tipo Camiseta.
     */
    @Override
    public Page<Camiseta> listarPorTipo(TipoCamiseta tipoCamiseta, Pageable pageable) {
        List<Camiseta> camisetas = camisetaDao.listarPorTipo(tipoCamiseta);
        Page<Camiseta> camisetasPorTipoPage = new PageImpl<>(camisetas);
        return camisetasPorTipoPage;
    }
}