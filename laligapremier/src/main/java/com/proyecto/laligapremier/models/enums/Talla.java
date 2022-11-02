package com.proyecto.laligapremier.models.enums;

import com.proyecto.laligapremier.models.entity.Camiseta;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Getter
public enum Talla {
    XS ("XS"),
    S ("S"),
    M ("M"),
    L("L"),
    XL("XL"),
    XXL("XXL");

    private final String talla;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Camiseta> camisetas;
    
    Talla(String talla){
        this.talla = talla; 
    }
}
