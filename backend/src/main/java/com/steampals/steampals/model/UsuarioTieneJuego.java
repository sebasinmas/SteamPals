package com.steampals.steampals.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "usuario_tiene_juego",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"usuario_id", "juego_id"})
        })
public class UsuarioTieneJuego {

    /**
     * Identificador único de la relación usuario-juego.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="juego_id")
    private Juego juego;
    /**
     * Cantidad de horas jugadas por el usuario en este juego.
     */
    private int horasJugadas;

    /**
     * Cantidad de logros obtenidos por el usuario en este juego.
     */
    private int cantidadDeLogros;
}
