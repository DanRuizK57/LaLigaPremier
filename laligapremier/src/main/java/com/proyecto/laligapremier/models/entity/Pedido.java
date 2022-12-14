package com.proyecto.laligapremier.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase Entidad <i>@Entity</i> que representa la tabla pedidos en la base de datos.
 * Getters y Setters fueron generados con la etiqueta de lombok.
 */

@Entity
@Getter
@Setter
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ItemPedido> items;

    @Column(name = "num_camisetas")
    private Integer numCamisetas;

    @Column(name = "precio_total")
    private Integer precioTotal;

    @Column(name = "codigo")
    private String codigo;

    public Pedido() {
        this.items = new ArrayList<ItemPedido>();
    }

    public Pedido(Long id, String codigo) {
        this.id = id;
        this.codigo = codigo;
        this.items = new ArrayList<ItemPedido>();
    }

    @PrePersist
    public void prePersist() {
        fecha = new Date();
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", usuario=" + usuario +
                ", numCamisetas=" + numCamisetas +
                ", precioTotal=" + precioTotal +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}
