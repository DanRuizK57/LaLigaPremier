package com.proyecto.laligapremier.models.entity;

import com.proyecto.laligapremier.models.enums.Talla;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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

    @NotNull
    private Talla talla;

    @NotNull
    @Min(1)
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public ItemPedido() {
    }

    public Integer calcularPrecioItem(){
        return camiseta.getPrecio() * cantidad;
    }
}
