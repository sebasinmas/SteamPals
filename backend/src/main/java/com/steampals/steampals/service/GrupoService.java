package com.steampals.steampals.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.repository.UsuarioRepository;
import com.steampals.steampals.repository.MatchUsuarioRepository;

import com.steampals.steampals.model.Grupo;
import com.steampals.steampals.repository.GrupoRepository;

@Service
public class GrupoService {
    private final GrupoRepository grupoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MatchUsuarioRepository matchUsuarioRepository;

    public GrupoService(GrupoRepository grupoRepository, UsuarioRepository usuarioRepository, MatchUsuarioRepository matchUsuarioRepository) {
        this.grupoRepository = grupoRepository;
        this.usuarioRepository = usuarioRepository;
        this.matchUsuarioRepository = matchUsuarioRepository;
    }

    public List<Grupo> obtenerGrupos(){
        return grupoRepository.findAll();
    }

    public Grupo guardarGrupo(Grupo grupo){
        return grupoRepository.save(grupo);
    }

    // Obtener los grupos a los que pertenece el usuario (por email)
    public List<Grupo> obtenerGruposPorEmailUsuario(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) return List.of();
        Usuario usuario = usuarioOpt.get();
        // Suponiendo que tienes un método en GrupoRepository para esto, si no, filtra en memoria
        return grupoRepository.findByMiembrosContaining(usuario);
    }

    // Crear un grupo y agregar al usuario como miembro
    public Grupo crearGrupo(Grupo grupo, String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) throw new IllegalArgumentException("Usuario no encontrado");
        Usuario usuario = usuarioOpt.get();
        grupo.getMiembros().add(usuario);
        return grupoRepository.save(grupo);
    }

    // Eliminar un grupo (solo si el usuario es miembro)
    public boolean eliminarGrupo(Long grupoId, String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        Optional<Grupo> grupoOpt = grupoRepository.findById(grupoId);
        if (usuarioOpt.isEmpty() || grupoOpt.isEmpty()) return false;
        Usuario usuario = usuarioOpt.get();
        Grupo grupo = grupoOpt.get();
        if (grupo.getMiembros().contains(usuario)) {
            grupoRepository.delete(grupo);
            return true;
        }
        return false;
    }

    // Invitar usuario matcheado a grupo
    public boolean invitarUsuarioAGrupo(Long grupoId, Long usuarioId, String email) {
        Optional<Usuario> invitadorOpt = usuarioRepository.findByEmail(email);
        Optional<Usuario> invitadoOpt = usuarioRepository.findById(usuarioId);
        Optional<Grupo> grupoOpt = grupoRepository.findById(grupoId);
        if (invitadorOpt.isEmpty() || invitadoOpt.isEmpty() || grupoOpt.isEmpty()) return false;
        Usuario invitador = invitadorOpt.get();
        Usuario invitado = invitadoOpt.get();
        Grupo grupo = grupoOpt.get();
        // Solo permite si el invitador es miembro
        if (!grupo.getMiembros().contains(invitador)) return false;
        // Verificar si invitador e invitado están matcheados
        boolean sonMatcheados = matchUsuarioRepository.findMatchBetween(invitador, invitado).isPresent();
        if (!sonMatcheados) return false;
        grupo.getMiembros().add(invitado);
        grupoRepository.save(grupo);
        return true;
    }

    // Eliminar usuario de grupo (solo si el que elimina es miembro)
    public boolean eliminarUsuarioDeGrupo(Long grupoId, Long usuarioId, String email) {
        Optional<Usuario> solicitanteOpt = usuarioRepository.findByEmail(email);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Grupo> grupoOpt = grupoRepository.findById(grupoId);
        if (solicitanteOpt.isEmpty() || usuarioOpt.isEmpty() || grupoOpt.isEmpty()) return false;
        Usuario solicitante = solicitanteOpt.get();
        Usuario usuario = usuarioOpt.get();
        Grupo grupo = grupoOpt.get();
        if (!grupo.getMiembros().contains(solicitante)) return false;
        boolean removed = grupo.getMiembros().remove(usuario);
        if (removed) {
            grupoRepository.save(grupo);
        }
        return removed;
    }
}
