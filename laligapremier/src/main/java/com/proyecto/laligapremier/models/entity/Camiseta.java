package com.proyecto.laligapremier.models.entity;
import com.proyecto.laligapremier.models.enums.TipoCamiseta;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;


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

    @Column(name = "dorsal" )
    private Integer dorsal;

    @Column(name = "jugador" )
    private String jugador ;

    @Column(name = "temporada" , nullable = false)
    private String temporada ;

    @Column(name = "precio" , nullable = false)
    private Integer precio;

    @Column(name = "imagen")
    private String imagen;

    @Enumerated(EnumType.STRING)
    @Column(name = "tallas")
    private Talla talla;

    @Enumerated(EnumType.STRING)
    @Column(name = "marca")
    private Marca marca;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_camista")
    private TipoCamiseta tipoCamiseta;


    private String prueba = "probando cosas para el merge ";
    

    private static final long serialVersionUID = 1L;
}

