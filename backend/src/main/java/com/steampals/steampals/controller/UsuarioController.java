package com.steampals.steampals.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UsuarioController {
    @GetMapping("/usuario")
    public String getUsuario() {
        return "Usuario endpoint reached!";
    }
    @PutMapping("/usuario")
    public String putUsuario() {
        return "Usuario put endpoint reached!";
    }
}
