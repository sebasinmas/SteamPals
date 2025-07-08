package com.steampals.steampals.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.steampals.steampals.dto.UsuarioDTO;
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
    public ResponseEntity<List<Usuario>> obtenerSugerencias(@PathVariable Long usuarioId) {
        return usuarioRepo.findById(usuarioId)
            .map(usuario -> ResponseEntity.ok(matchService.obtenerPerfilesParaUsuario(usuario)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/like")
    public ResponseEntity<Void> darLike(@RequestParam Long actualId, @RequestParam Long objetivoId) {
        if (actualId.equals(objetivoId)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Usuario> actualOpt = usuarioRepo.findById(actualId);
        Optional<Usuario> objetivoOpt = usuarioRepo.findById(objetivoId);
        if (actualOpt.isEmpty() || objetivoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        matchService.darLike(actualOpt.get(), objetivoOpt.get());
        return ResponseEntity.ok().build();
    }
}
