package com.steampals.steampals.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BibliotecaController {
    @GetMapping("/biblioteca")
    public String getBiblioteca() {
        return "Biblioteca endpoint reached!";
    }
    @PutMapping("/biblioteca")
    public String putBiblioteca() {
        return "Biblioteca put endpoint reached!";
    }
}