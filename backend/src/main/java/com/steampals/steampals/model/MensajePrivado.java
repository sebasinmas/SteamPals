package com.steampals.steampals.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
@Table(name="mensaje_privado")
public class MensajePrivado {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    
    private String mensaje;

    private LocalDateTime fechaEnvio;

    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private Usuario emisor;

    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private Usuario receptor;
    
}