package com.steampals.steampals.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * Representa un mensaje privado intercambiado entre dos usuarios.
 * Contiene el contenido del mensaje, información del emisor y del receptor.
 *
 * Campos:
 * <ul>
 *   <li>id - Identificador único del mensaje privado.</li>
 *   <li>mensaje - El contenido del mensaje.</li>
 *   <li>emisor - El usuario que envía el mensaje.</li>
 *   <li>receptor - El usuario que recibe el mensaje.</li>
 * </ul>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MensajePrivado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * El contenido del mensaje.
     */
    private String mensaje;

    /**
     * El usuario que envía el mensaje.
     */
    private Usuario emisor;

    /**
     * El usuario que recibe el mensaje.
     */
    private Usuario receptor;
}
