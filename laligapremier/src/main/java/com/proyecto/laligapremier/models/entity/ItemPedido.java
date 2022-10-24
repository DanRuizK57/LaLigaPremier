package com.proyecto.laligapremier.models.entity;

import com.proyecto.laligapremier.models.enums.Talla;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "items_pedidos")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Camiseta camiseta;

    private Talla talla;

    private Integer cantidad;

    public ItemPedido() {
    }

    public Integer calcularPrecioItem(){
        return camiseta.getPrecio() * cantidad;
    }
}
