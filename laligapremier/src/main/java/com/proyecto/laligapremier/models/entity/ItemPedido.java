package com.proyecto.laligapremier.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "pedidos_items")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camiseta_id")
    private Camiseta camiseta;

    public Double calcularTotalItem() {
        return cantidad.doubleValue() * camiseta.getPrecio();
    }


}
