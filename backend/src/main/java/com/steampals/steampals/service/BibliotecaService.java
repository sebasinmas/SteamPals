package com.steampals.steampals.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steampals.steampals.model.Biblioteca;
import com.steampals.steampals.repository.BibliotecaRepository;

@Service
public class BibliotecaService {
    @Autowired
    BibliotecaRepository bibliotecaRepository;
    public ArrayList<Biblioteca> obtenerBibliotecas(){
        return (ArrayList<Biblioteca>)bibliotecaRepository.findAll();
    }
    public Biblioteca guardarBiblioteca(Biblioteca biblioteca){
        return bibliotecaRepository.save(biblioteca);
    }
    
}
