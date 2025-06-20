package com.steampals.steampals.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.repository.UsuarioRepository;
import com.steampals.steampals.service.MatchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final UsuarioRepository usuarioRepo;

    @GetMapping("/sugerencias/{usuarioId}")
    public List<Usuario> obtenerSugerencias(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId).orElseThrow();
        return matchService.obtenerPerfilesParaUsuario(usuario);
    }

    @PostMapping("/like")
    public void darLike(@RequestParam Long actualId, @RequestParam Long objetivoId) {
        Usuario actual = usuarioRepo.findById(actualId).orElseThrow();
        Usuario objetivo = usuarioRepo.findById(objetivoId).orElseThrow();
        matchService.darLike(actual, objetivo);
    }
}
