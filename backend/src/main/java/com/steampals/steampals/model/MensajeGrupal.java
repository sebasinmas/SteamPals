package com.steampals.steampals.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Representa un mensaje enviado dentro de un grupo.
 * 
 * Esta entidad almacena la información básica de un mensaje grupal,
 * incluyendo el contenido del mensaje, el emisor y un identificador único.
 * 
 * <p>
 * Utiliza anotaciones de JPA para su persistencia en base de datos.
 * </p>
 * 
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MensajeGrupal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private Usuario emisor;
}
