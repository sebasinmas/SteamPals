package com.steampals.steampals.model;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String mensaje;

    @ManyToOne(targetEntity = Usuario.class, fetch = FetchType.LAZY)
    private Usuario emisor;
    
    @ManyToOne(targetEntity = Usuario.class, fetch = FetchType.LAZY)
    private Usuario receptor;
}
