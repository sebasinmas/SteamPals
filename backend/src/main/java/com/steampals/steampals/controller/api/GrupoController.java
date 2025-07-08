package com.steampals.steampals.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
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
    private final GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping("/grupo")
    public ResponseEntity<List<Grupo>> getGrupos() {

        return ResponseEntity.ok(grupoService.obtenerGrupos());
    }
    @PutMapping("/grupo")
    public ResponseEntity<String> putGrupo() {
        return ResponseEntity.ok("Grupo put endpoint reached!");
    }
    @PostMapping("/grupo")
    public ResponseEntity<Grupo> guardaGrupo(@RequestBody Grupo grupo){
        return ResponseEntity.ok(this.grupoService.guardarGrupo(grupo));
    }
}