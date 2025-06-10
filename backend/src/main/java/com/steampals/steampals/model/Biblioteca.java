package com.steampals.steampals.model;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Biblioteca {
    private long id;
    private int numeroDeJuegos;
    private int cantidadDeLogros;
    @OneToMany(targetEntity=Juego.class,fetch=FetchType.LAZY)
    private Juego juego;
    @OneToOne(targetEntity=Usuario.class,fetch=FetchType.LAZY)
    private Usuario usuario;
}
