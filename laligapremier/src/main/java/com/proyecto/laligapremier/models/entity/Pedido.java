package com.proyecto.laligapremier.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
                ", items=" + items +
                ", numCamisetas=" + numCamisetas +
                ", precioTotal=" + precioTotal +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}
