package com.steampals.steampals.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("/api")
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
