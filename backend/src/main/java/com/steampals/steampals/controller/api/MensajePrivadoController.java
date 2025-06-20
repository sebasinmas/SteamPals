package com.steampals.steampals.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.steampals.steampals.model.MensajePrivado;
import com.steampals.steampals.service.MensajePrivadoService;

@RestController
@RequestMapping("/api/mensajes")
public class MensajePrivadoController {
    @Autowired
    private MensajePrivadoService mensajeService;

    @PostMapping("/enviar")
    public MensajePrivado enviar(@RequestParam Long emisorId,@RequestParam Long receptorId,@RequestParam String mensaje) {
        return mensajeService.enviarMensajePorId(emisorId, receptorId, mensaje);
    }
}
