package com.steampals.steampals.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * Objeto de Transferencia de Datos (DTO) para representar un mensaje privado entre usuarios.
 * Contiene información sobre el contenido del mensaje, la fecha, el emisor y el receptor.
 *
 * <p>Campos:</p>
 * <ul>
 *   <li>id: Identificador único del mensaje privado.</li>
 *   <li>contenido: El contenido del mensaje.</li>
 *   <li>fecha: La fecha en que se envió el mensaje.</li>
 *   <li>nombreEmisor: El nombre del emisor.</li>
 *   <li>nombreReceptor: El nombre del receptor.</li>
 * </ul>
 *
 * <p>Utiliza anotaciones de Lombok para la generación de código boilerplate.</p>
 */

@Getter
@Setter
@AllArgsConstructor
public class MensajePrivadoDTO {
private Long id;
    /**
     * Contenido del mensaje privado.
     */
    private String contenido;
    /**
     * Fecha en que se envió el mensaje.
     */
    private LocalDate fecha;
    /**
     * Nombre del usuario que envió el mensaje.
     */
    private String nombreEmisor;     
/**
     * Nombre del usuario que recibió el mensaje.
     */
    private String nombreReceptor; 
}
