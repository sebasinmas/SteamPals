package com.steampals.steampals.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioTieneJuego {

    /**
     * Identificador único de esta relación usuario-juego.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * El usuario que posee o ha jugado este juego.
     */
    @ManyToOne(optional = false)
    private Usuario usuario;

    /**
     * El juego que ha sido jugado por el usuario.
     */
    @ManyToOne(optional = false)
    private Juego juego;

    /**
     * Total de horas jugadas por el usuario en este juego.
     */
    private int horasJugadas;
}
