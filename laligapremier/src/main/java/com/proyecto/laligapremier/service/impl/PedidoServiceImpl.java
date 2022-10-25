package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.dao.IPedidoDao;
import com.proyecto.laligapremier.models.entity.Pedido;
import com.proyecto.laligapremier.service.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl implements IPedidoService {

    @Autowired
    private IPedidoDao pedidoDao;

    @Override
    public List<Pedido> findAll() {
        return (List<Pedido>) pedidoDao.findAll();
    }

    @Override
    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoDao.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        pedidoDao.deleteById(id);
    }

    @Override
    public Pedido findOne(Long id) {
        return pedidoDao.findById(id).orElse(null);
    }

    @Override
    public void save(Pedido pedido) {
        pedidoDao.save(pedido);
    }
}
