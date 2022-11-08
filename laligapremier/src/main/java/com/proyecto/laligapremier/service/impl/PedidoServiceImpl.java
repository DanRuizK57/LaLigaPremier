package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.dao.IPedidoDao;
import com.proyecto.laligapremier.models.entity.Pedido;
import com.proyecto.laligapremier.service.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la clase de IPedidoService, donde se dan lógica a sus métodos.
 */

@Service
public class PedidoServiceImpl implements IPedidoService {

    /**
     * Inyección de la clase IPedidoDao para acceder a sus métodos y así realizar consultas a la base de datos.
     */
    @Autowired
    private IPedidoDao pedidoDao;

    /**
     * Método que guarda en una lista todos los objetos del tipo Pedido disponibles en la base de datos.
     * @return Lista de objetos del tipo Pedido.
     */
    @Override
    public List<Pedido> findAll() {
        return (List<Pedido>) pedidoDao.findAll();
    }

    /**
     * Método que guarda en una lista todos los objetos del tipo Pedido disponibles en la base de datos,
     * pero los distribuye según el número de elementos indicados en el paginador.
     * @param pageable Objeto del paginador, usado para establecer el número de pedidos que se muestran por página.
     * @return Lista paginada de objetos del tipo Pedido.
     */
    @Override
    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoDao.findAll(pageable);
    }

    /**
     * Método que elimina un objeto del tipo Pedido según su id en la base de datos.
     * @param id Objeto de tipo Long, usado para elegir el pedido a eliminar.
     */
    @Override
    public void delete(Long id) {
        pedidoDao.deleteById(id);
    }

    /**
     * Método que busca un objeto del tipo Pedido según su id en la base de datos.
     * @param id Objeto de tipo Long, usado para encontrar el pedido seleccionado.
     * @return Objeto del tipo Pedido.
     */
    @Override
    public Pedido findOne(Long id) {
        return pedidoDao.findById(id).orElse(null);
    }

    /**
     * Método que guarda un objeto del tipo Pedido en la base de datos.
     * @param pedido Objeto de tipo Pedido, usado para guardarlo en la base de datos.
     */
    @Override
    public void save(Pedido pedido) {
        pedidoDao.save(pedido);
    }
}
