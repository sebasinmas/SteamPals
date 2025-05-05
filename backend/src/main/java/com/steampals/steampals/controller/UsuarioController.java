package com.steampals.steampals.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UsuarioController {
    @GetMapping("/usuario")
    public String getUsuario() {
        return "Usuario endpoint reached!";
    }
}
