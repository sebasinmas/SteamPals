package com.steampals.steampals.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Reporte")
public class Reporte {
    /**
     * ID único del reporte, generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Motivo del reporte, describe la razón por la cual se está reportando al
     * usuario.
     */
    private String motivo;

    @ManyToOne
    private Usuario reportado;
    @ManyToOne
    private Usuario reportador;
}
