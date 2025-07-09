package com.steampals.steampals.controller.api;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.steampals.steampals.model.Grupo;
import com.steampals.steampals.service.GrupoService;


@RestController
@RequestMapping("/api/grupo")
public class GrupoController {
    private final GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    /**
     * Obtener los grupos a los que pertenece el usuario autenticado
     */
    @GetMapping
    public ResponseEntity<List<Grupo>> getGruposDelUsuario(Authentication authentication) {
        String email = authentication.getName();
        List<Grupo> grupos = grupoService.obtenerGruposPorEmailUsuario(email);
        return ResponseEntity.ok(grupos);
    }

    /**
     * Crear un nuevo grupo y agregar al usuario autenticado como miembro
     */
    @PostMapping
    public ResponseEntity<Grupo> crearGrupo(@RequestBody Grupo grupo, Authentication authentication) {
        String email = authentication.getName();
        Grupo creado = grupoService.crearGrupo(grupo, email);
        return ResponseEntity.ok(creado);
    }

    /**
     * Eliminar un grupo (solo si el usuario es miembro o admin del grupo)
     */
    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> eliminarGrupo(@PathVariable Long grupoId, Authentication authentication) {
        String email = authentication.getName();
        boolean eliminado = grupoService.eliminarGrupo(grupoId, email);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    /**
     * Invitar a un usuario previamente matcheado al grupo
     */
    @PostMapping("/{grupoId}/invitar/{usuarioId}")
    public ResponseEntity<String> invitarUsuario(@PathVariable Long grupoId, @PathVariable Long usuarioId, Authentication authentication) {
        String email = authentication.getName();
        boolean invitado = grupoService.invitarUsuarioAGrupo(grupoId, usuarioId, email);
        if (invitado) {
            return ResponseEntity.ok("Usuario invitado correctamente");
        } else {
            return ResponseEntity.badRequest().body("No se pudo invitar al usuario (no matcheado o no autorizado)");
        }
    }

    /**
     * Eliminar a un usuario del grupo
     */
    @DeleteMapping("/{grupoId}/miembros/{usuarioId}")
    public ResponseEntity<String> eliminarUsuarioDelGrupo(@PathVariable Long grupoId, @PathVariable Long usuarioId, Authentication authentication) {
        String email = authentication.getName();
        boolean eliminado = grupoService.eliminarUsuarioDeGrupo(grupoId, usuarioId, email);
        if (eliminado) {
            return ResponseEntity.ok("Usuario eliminado del grupo");
        } else {
            return ResponseEntity.status(403).body("No autorizado o usuario no pertenece al grupo");
        }
    }
}