package com.proyecto.laligapremier.models.entity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "camisetas")
@Getter
@Setter
public class Camiseta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false)
    private Long id;

    @Column(name = "equipo" , nullable = false)
    private String equipo;

    @Column(name = "liga", nullable = false)
    private String liga;

    @Column(name = "dorsal" , nullable = false)
    private Integer dorsar;

    @Column(name = "jugador" , nullable = false)
    private String jugador ;

    @Column(name = "temporada" , nullable = false)
    private String temporada ;

    @Column(name = "precio" , nullable = false)
    private Integer precio;

    @Column(name = "imagen" , nullable = false)
    private String imagen;

    private static final long serialVersionUID = 1L;
}

