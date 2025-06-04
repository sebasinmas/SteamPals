package com.steampals.steampals.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("/api")
public class GrupoController {
    @GetMapping("/grupo")
    public String getGrupo() {
        return "Grupo endpoint reached!";
    }
}