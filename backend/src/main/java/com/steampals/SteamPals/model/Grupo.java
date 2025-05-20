package com.steampals.SteamPals.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Grupo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String fechaCreacion;
    private String descripcion;
    private List<Usuario> miembros= new ArrayList<Usuario>();
    private List<MensajeGrupal> mensajes = new ArrayList<MensajeGrupal>();


    public void AgregarMiembro(Usuario u){
        if(miembros.contains(u)){
            miembros.add(u);
        }
    }
    public void eliminarMiembro(Usuario u){
        if(miembros.contains(u)){
            miembros.remove(u);
        }
    }
    public void enviarMensaje(MensajeGrupal m){
        mensajes.add(m);
    }

}