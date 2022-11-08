package com.proyecto.laligapremier.models.entity;
import com.proyecto.laligapremier.models.enums.TipoCamiseta;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.proyecto.laligapremier.models.enums.Marca;
import com.proyecto.laligapremier.models.enums.Talla;

/**
 * Clase Entidad Camiseta que representa la tabla camisetas en la base de datos
 * Getters y Setters fueron generados con la etiqueta de lombok
 */
@Entity
@Table(name = "camisetas")
@Getter
@Setter
public class Camiseta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "equipo" )
    private String equipo;

    @NotBlank
    @Column(name = "liga" )
    private String liga;

    @Column(name = "dorsal" )
    private Integer dorsal;

    @Column(name = "jugador" )
    private String jugador ;

    @NotBlank
    @Column(name = "temporada" , nullable = false)
    private String temporada ;

    @NotNull
    @Column(name = "precio", nullable = false)
    @DecimalMin(value = "0", message = "*Price has to be non negative number")
    private Integer precio;

    @Column(name = "imagen")
    private String imagen;

    @NotBlank
    @Column(name = "nombre")
    private String nombre;

    @NotBlank
    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tallas")
    private Talla talla;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marca")
    private Marca marca;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_camiseta")
    private TipoCamiseta tipoCamiseta;

    private static final long serialVersionUID = 1L;
}

