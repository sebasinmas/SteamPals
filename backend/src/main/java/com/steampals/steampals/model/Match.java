package com.steampals.steampals.model;

import java.util.List;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Match {

    @Id
    @GeneratedValue
    private Long id;

    /** Usuario que inició la interacción. */
    @ManyToOne
    private Usuario usuario1;

    /** Usuario que recibió la interacción. */
    @ManyToOne
    private Usuario usuario2;

    /** Si el usuario1 dio like. */
    private boolean usuario1Like;

    /** Si el usuario2 dio like. */
    private boolean usuario2Like;

    /** True si ambos usuarios dieron like. */
    private boolean completo;
}