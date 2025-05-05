package com.steampals.steampals.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String usuario;
    private String email;
    private String contrasenia;
    private int edad;
    private String pais;
    private String descripcion;
    
}