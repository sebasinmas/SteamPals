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
public class Juego {
    private Long id;
    private String nombre;
    private String descripcion;
    private String desarrollador;
    private String genero;
    private String fechaDeLanzamiento;
    private String imagenUrl;
}
