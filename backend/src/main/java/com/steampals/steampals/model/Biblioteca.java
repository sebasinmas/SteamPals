package com.steampals.steampals.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="biblioteca")
public class Biblioteca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true,nullable=false)
    private long id;
    private int numeroDeJuegos;
    private int cantidadDeLogros;
    @OneToMany(targetEntity=Juego.class,fetch=FetchType.LAZY)
    private Juego juego;
    @OneToOne(targetEntity=Usuario.class,fetch=FetchType.LAZY)
    private Usuario usuario;
}
