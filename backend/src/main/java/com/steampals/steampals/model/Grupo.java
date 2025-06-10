package com.steampals.steampals.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToMany;
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
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private LocalDate fechaCreacion;
    private String descripcion;

    @OneToMany(mappedBy = "grupo")
    private List<MensajeGrupal> mensajes = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Usuario> miembros = new HashSet<>();
    
        public void AgregarMiembro(Usuario u) {
        if (miembros.contains(u)) {
            miembros.add(u);
        }
    }

    public void eliminarMiembro(Usuario u) {
        if (miembros.contains(u)) {
            miembros.remove(u);
        }
    }

    public void enviarMensaje(MensajeGrupal m) {
        mensajes.add(m);
    }
}