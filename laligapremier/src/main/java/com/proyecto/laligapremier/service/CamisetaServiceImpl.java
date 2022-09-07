package com.proyecto.laligapremier.service;

import com.proyecto.laligapremier.models.dao.ICamisetaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Service
public class CamisetaServiceImpl {

    @Autowired
    private ICamisetaDao camisetaDao;

    public String listar(@RequestParam(value = "id") Long id, Map<String , Object> model , RedirectAttributes flash){
        
        return "listar";
    }

}
