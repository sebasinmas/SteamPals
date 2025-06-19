package com.steampals.steampals.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Esta clase representa un Data Transfer Object (DTO) para el usuario.
 * Se utiliza para transferir datos entre la capa de presentación y la capa de
 * servicio.
 * Contiene los atributos necesarios para el registro y autenticación de un
 * usuario.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private String usuario;
    private String email;
    private String contrasenia;
    private int edad;
    private String pais;
    private String descripcion;
}