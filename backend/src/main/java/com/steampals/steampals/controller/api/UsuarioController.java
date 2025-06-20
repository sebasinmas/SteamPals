package com.steampals.steampals.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.steampals.steampals.dto.RegistroUsuarioDTO;
import com.steampals.steampals.dto.UsuarioDTO;
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
    public ResponseEntity<?> crearUsuario(@RequestBody RegistroUsuarioDTO registroUsuarioDTO) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(registroUsuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con ID: " + nuevoUsuario.getId());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/usuario")
    public ResponseEntity<?> eliminarUsuario(@RequestBody String email) {
        try {
            usuarioService.eliminarUsuario(email);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/usuario")
    public ResponseEntity<?> actualizarUsuario(@RequestBody RegistroUsuarioDTO registroUsuarioDTO) {
        try {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(registroUsuarioDTO);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/usuario/me")
    public ResponseEntity<?> getPerfilUsuario(Authentication authentication) {
        // Obtén el usuario autenticado desde el objeto Authentication
        String email = authentication.getName(); // email desde el token
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(usuarioDTO);
    }
    @GetMapping("/{id}/perfil-publico")
    public ResponseEntity<UsuarioDTO> verPerfilPublico(@PathVariable Long id) {
        try {
            UsuarioDTO dto = usuarioService.obtenerPerfilPublico(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
