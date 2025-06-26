package com.steampals.steampals.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steampals.steampals.model.MensajePrivado;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.repository.MensajePrivadoRepository;
import com.steampals.steampals.repository.UsuarioRepository;

@Service
public class MensajePrivadoService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MensajePrivadoRepository mensajePrivadoRepository;

    public MensajePrivado enviarMensajePorId(Long emisorId, Long receptorId, String contenido) {
        Usuario emisor = usuarioRepository.findById(emisorId)
                .orElseThrow(() -> new RuntimeException("Emisor no encontrado"));
        Usuario receptor = usuarioRepository.findById(receptorId)
                .orElseThrow(() -> new RuntimeException("Receptor no encontrado"));

        MensajePrivado msg = new MensajePrivado();
        msg.setEmisor(emisor);
        msg.setReceptor(receptor);
        msg.setMensaje(contenido);
        msg.setFechaEnvio(LocalDateTime.now());

        return mensajePrivadoRepository.save(msg);
    }
}
