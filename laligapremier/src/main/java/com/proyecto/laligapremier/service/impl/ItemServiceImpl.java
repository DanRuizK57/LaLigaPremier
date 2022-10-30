package com.proyecto.laligapremier.service.impl;

import com.proyecto.laligapremier.models.dao.IItemDao;
import com.proyecto.laligapremier.models.entity.Item;
import com.proyecto.laligapremier.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    private IItemDao itemDao;

    @Override
    public void save(Item item) {
        itemDao.save(item);
    }

    @Override
    public List<Item> listar() {
        return (List<Item>) itemDao.findAll();
    }

    @Override
    public void delete() {

    }
}
