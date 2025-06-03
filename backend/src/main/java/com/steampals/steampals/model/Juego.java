package com.steampals.steampals.model;

import java.util.List;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Juego {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String img;

    @OneToMany(targetEntity = Biblioteca.class, fetch = FetchType.LAZY)
    private List<Biblioteca> bibliotecas;
    @OneToMany(targetEntity = UsuarioTieneJuego.class, fetch = FetchType.LAZY)
    private List<UsuarioTieneJuego> UsuarioTieneJuego;

}
