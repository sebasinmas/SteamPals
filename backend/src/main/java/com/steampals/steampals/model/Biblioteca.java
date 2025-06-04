package com.steampals.steampals.model;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Biblioteca {
    private Long id;
    private int numeroDeJuegos;
    private int cantidadDeLogros;
}
