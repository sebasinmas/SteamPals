package com.steampals.steampals.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Builder
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String fechaCreacion;
    // private String descripcion;

    @OneToMany(targetEntity = Usuario.class, fetch = FetchType.LAZY)
    private List<Usuario> miembros;
    @OneToMany(targetEntity = MensajeGrupal.class, fetch = FetchType.LAZY)
    private List<MensajeGrupal> mensajes;


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