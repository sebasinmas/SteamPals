package com.steampals.steampals.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steampals.steampals.model.Grupo;
import com.steampals.steampals.repository.GrupoRepository;

@Service
public class GrupoService {
    @Autowired
    GrupoRepository grupoRepository;
    public ArrayList<Grupo> obtenerGrupos(){
        return (ArrayList<Grupo>) grupoRepository.findAll();
    }
    public Grupo guardarGrupo(Grupo grupo){
        return grupoRepository.save(grupo);
    }
}
