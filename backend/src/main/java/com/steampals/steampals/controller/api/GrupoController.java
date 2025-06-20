package com.steampals.steampals.controller.api;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.steampals.steampals.model.Grupo;
import com.steampals.steampals.service.GrupoService;


@RestController
@RequestMapping("/api/grupo")
public class GrupoController {
    @Autowired
    GrupoService grupoService;
    @GetMapping("/grupo")
    public ArrayList<Grupo> getGrupos() {
        return grupoService.obtenerGrupos();
    }
    @PutMapping("/grupo")
    public String putGrupo() {
        return "Grupo put endpoint reached!";
    }
    @PostMapping("/grupo")
    public Grupo guardaGrupo(@RequestBody Grupo grupo){
        return this.grupoService.guardarGrupo(grupo);
    }
}