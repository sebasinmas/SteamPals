package com.steampals.steampals.model;

import java.util.List;

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
public class UsuarioTieneJuego {
    private List<Usuario> usuarios;
    //Usuarios con este juego
    private float horasJugadas;
}
