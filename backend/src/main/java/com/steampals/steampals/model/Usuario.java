package com.steampals.steampals.model;

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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String usuario;
    private String email;
    private String contrasenia;
    private int edad;
    private String pais;
    private String descripcion;
    private List<Mensaje> listaMsgEnv;
    //Mensajes enviados
    private List<Mensaje> listaMsgRec;
    //Mensajes recibidos
    private List<Match> listaMatchs;
    //Matchs en las que el usuario participó
    private List<MensajeGrupal> listaMsgGrupalEnv;
    //Mensajes grupales enviados
    private List<MensajeGrupal> listaMsgGrupalRec;
    //Mensajes grupales recibidos
    private Biblioteca bibliotecaSteam;
    private List<Grupo> listaGruposPertenecientes;
    //Grupos a los que pertenece el usuario

}
