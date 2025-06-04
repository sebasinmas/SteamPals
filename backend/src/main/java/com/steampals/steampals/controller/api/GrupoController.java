package com.steampals.steampals.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/grupo")
public class GrupoController {
    @GetMapping("/grupo")
    public String getGrupo() {
        return "Grupo endpoint reached!";
    }
    @PostMapping("/grupo")
    public String postGrupo() {
        return "Grupo post endpoint reached!";
    }
}