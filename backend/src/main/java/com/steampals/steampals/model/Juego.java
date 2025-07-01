package com.steampals.steampals.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "juego", uniqueConstraints = {
        @UniqueConstraint(columnNames = "app_id")
})
public class Juego {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "app_id")
    private Long appId;

    private String nombre;

    @OneToMany(mappedBy = "juego")
    private Set<UsuarioTieneJuego> usuarioTieneJuego = new HashSet<>();
    
    /**
     * Descripción del juego.
     */
    private String descripcion;

    /**
     * Nombre del desarrollador del juego.
     */
    private String desarrollador;

    /**
     * Género del juego.
     */
    private String genero;

    /**
     * Fecha de lanzamiento del juego.
     */
    private String fechaDeLanzamiento;

    /**
     * URL de la imagen del juego.
     */
    private String imagenUrl;

}
