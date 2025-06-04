package com.steampals.steampals.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class UsuarioController {
    @GetMapping("/usuario")
    public String getUsuario() {
        return "Usuario get endpoint reached!";
    }
    @PutMapping("/usuario")
    public String putUsuario() {
        return "Usuario put endpoint reached!";
    }
}
