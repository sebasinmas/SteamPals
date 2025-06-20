package com.steampals.steampals.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioDTO {

    private String nombreUsuario;
 
    private String email;
  
    private String contrasenia;

    private int edad;

    private String pais;

}
