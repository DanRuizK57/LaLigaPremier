package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.dao.IItemPedidoDao;
import com.proyecto.laligapremier.models.entity.ItemPedido;
import com.proyecto.laligapremier.service.IItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoServiceImpl implements IItemPedidoService {

    @Autowired
    private IItemPedidoDao itemPedidoDao;

    @Override
    public List<ItemPedido> findAll() {
        return (List<ItemPedido>) itemPedidoDao.findAll();
    }

    @Override
    public void delete(Long id) {
        itemPedidoDao.deleteById(id);
    }

    @Override
    public ItemPedido findOne(Long id) {
        return itemPedidoDao.findById(id).orElse(null);
    }

    @Override
    public void save(ItemPedido item) {
        itemPedidoDao.save(item);
    }

    @Override
    public void flush() {
        itemPedidoDao.flush();
    }

}
