package com.steampals.steampals.model;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioTieneJuego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float horasJugadas;
}
