package com.steampals.steampals.model;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Biblioteca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int numeroDeJuegos;
    private int cantidadDeLogros;
    @OneToMany(targetEntity=Juego.class,fetch=FetchType.LAZY)
    private Juego juego;
    @OneToOne(targetEntity=Usuario.class,fetch=FetchType.LAZY)
    private Usuario usuario;
}
