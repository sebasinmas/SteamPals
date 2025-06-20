package com.steampals.steampals.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.steampals.steampals.dto.EliminarUsuarioDTO;
import com.steampals.steampals.dto.RegistroUsuarioDTO;
import com.steampals.steampals.dto.UsuarioDTO;
import com.steampals.steampals.dto.UsuarioUpdateDTO;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarUsuario(@RequestBody EliminarUsuarioDTO dto) {
        try {
            usuarioService.eliminarUsuario(dto.getEmail());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/usuario")
    public ResponseEntity<?> actualizarUsuario(@RequestBody UsuarioUpdateDTO updateDTO, Authentication authentication) {
        try {
            String email = authentication.getName(); // obtenido desde el JWT
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(email, updateDTO);
            return ResponseEntity.ok("Usuario actualizado con ID: " + usuarioActualizado.getId());
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
}
