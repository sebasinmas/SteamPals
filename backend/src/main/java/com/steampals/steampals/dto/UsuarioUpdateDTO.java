package com.steampals.steampals.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateDTO {
    private String nombreUsuario;
    private int edad;
    private String pais;
    private String descripcion;
}
