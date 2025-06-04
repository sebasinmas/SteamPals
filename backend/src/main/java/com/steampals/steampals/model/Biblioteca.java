package com.steampals.steampals.model;
import lombok.AllArgsConstructor;
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
}
