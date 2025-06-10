/**
 * Representa una coincidencia ("match") entre dos usuarios en la aplicación.
 * Un match se produce cuando ambos usuarios se gustan mutuamente.
 *
 * Utiliza anotaciones de JPA para persistencia y Lombok para generación de código boilerplate.
 */
package com.steampals.steampals.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatchUsuario {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario1;

    @ManyToOne
    @JoinColumn(name = "usuario2_id")
    private Usuario usuario2;

    /** Si el usuario1 dio like. */
    private boolean usuario1Like;

    /** Si el usuario2 dio like. */
    private boolean usuario2Like;

    /** True si ambos usuarios dieron like. */
    private boolean completo;
}