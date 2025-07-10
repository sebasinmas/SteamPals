package com.steampals.steampals.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.steampals.steampals.model.Grupo;
import com.steampals.steampals.model.MensajeGrupal;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.repository.GrupoRepository;
import com.steampals.steampals.repository.MensajeGrupalRepository;
import com.steampals.steampals.repository.UsuarioRepository;


@Service
public class MensajeGrupalService {

    private final MensajeGrupalRepository mensajeGrupalRepository;
    private final GrupoRepository grupoRepository;
    private final UsuarioRepository usuarioRepository;

    public MensajeGrupalService(MensajeGrupalRepository mensajeGrupalRepository,
                                GrupoRepository grupoRepository,
                                UsuarioRepository usuarioRepository) {
        this.mensajeGrupalRepository = mensajeGrupalRepository;
        this.grupoRepository = grupoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<MensajeGrupal> obtenerMensajesDeGrupo(Long grupoId) {
        return mensajeGrupalRepository.findByGrupoIdOrderByFechaEnvioAsc(grupoId);
    }

    public MensajeGrupal enviarMensaje(Long grupoId, Long emisorId, String texto) {
        Grupo grupo = grupoRepository.findById(grupoId).orElseThrow();
        Usuario emisor = usuarioRepository.findById(emisorId).orElseThrow();

        MensajeGrupal mensaje = new MensajeGrupal();
        mensaje.setGrupo(grupo);
        mensaje.setEmisor(emisor);
        mensaje.setMensaje(texto);
        mensaje.setFechaEnvio(LocalDateTime.now());

        return mensajeGrupalRepository.save(mensaje);
    }
}
