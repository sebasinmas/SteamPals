package com.steampals.steampals.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
@Table(name="mensaje_grupal")
public class MensajeGrupal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    private String mensaje;

    private LocalDateTime fechaEnvio;
    /**
     * Usuario que envió el mensaje.
     */
    @ManyToOne 
    @JoinColumn(name = "emisor_id")
    private Usuario emisor;

}
