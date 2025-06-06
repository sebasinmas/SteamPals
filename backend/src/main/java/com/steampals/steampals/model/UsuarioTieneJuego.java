package com.steampals.steampals.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
