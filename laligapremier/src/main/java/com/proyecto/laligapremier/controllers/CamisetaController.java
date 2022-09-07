package com.proyecto.laligapremier.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CamisetaController {

    @RequestMapping(name = "/index" )
    public String index(){
        return "index";
    }
}
