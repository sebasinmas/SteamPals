package com.steampals.steampals.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.steampals.steampals.dto.RegistroUsuarioDTO;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.service.UsuarioService;


@RestController
@RequestMapping("/api")
public class UsuarioController {
    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/usuario")
    public ResponseEntity<?> postUsuario(@RequestBody RegistroUsuarioDTO registroUsuarioDTO) {
         try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(registroUsuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con ID: " + nuevoUsuario.getId());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
