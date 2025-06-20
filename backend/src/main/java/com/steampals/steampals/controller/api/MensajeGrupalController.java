package com.steampals.steampals.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.steampals.steampals.model.MensajeGrupal;
import com.steampals.steampals.service.MensajeGrupalService;

@RestController
@RequestMapping("/api")
public class MensajeGrupalController {
    @Autowired
    private MensajeGrupalService mensajeGrupalService;

    @GetMapping("/{grupoId}")
    public List<MensajeGrupal> obtenerMensajes(@PathVariable Long grupoId) {
        return mensajeGrupalService.obtenerMensajesDeGrupo(grupoId);
    }

    @PostMapping("/enviar")
    public MensajeGrupal enviarMensaje(@RequestParam Long grupoId,
            @RequestParam Long emisorId,
            @RequestParam String mensaje) {
        return mensajeGrupalService.enviarMensaje(grupoId, emisorId, mensaje);
    }
}
