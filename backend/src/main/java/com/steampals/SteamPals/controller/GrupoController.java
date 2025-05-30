package com.steampals.steampals.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GrupoController {
    @GetMapping("/grupo")
    public String getGrupo() {
        return "Grupo endpoint reached!";
    }
}