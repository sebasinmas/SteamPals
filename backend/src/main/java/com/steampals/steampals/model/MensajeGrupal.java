package com.steampals.steampals.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class MensajeGrupal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true,nullable=false)
    private Long id;
    private String mensaje;

    @ManyToOne(targetEntity = Usuario.class, fetch = FetchType.LAZY)
    private Usuario emisor;
    @ManyToOne(targetEntity = Usuario.class, fetch = FetchType.LAZY)
    private Usuario receptor;
    @ManyToOne(targetEntity = Grupo.class, fetch = FetchType.LAZY)
    private Grupo grupo;

}
