package com.steampals.steampals.service;

<<<<<<< HEAD

import com.steampals.steampals.model.Juego;
import com.steampals.steampals.repository.JuegoRepository;
import org.springframework.web.client.RestTemplate;



=======
import org.springframework.stereotype.Service;

@Service
>>>>>>> ftr_luck
public class JuegoService {
    private final JuegoRepository juegoRepository;
    private final RestTemplate restTemplate;

    public JuegoService(JuegoRepository juegoRepository, RestTemplate restTemplate) {
        this.juegoRepository = juegoRepository;
        this.restTemplate = restTemplate;
    }

    public void addJuego(Juego juego) {
        if (juegoRepository.findById(juego.getId()).isPresent()) {
            throw new IllegalArgumentException("El juego ya existe en la base de datos.");
        }

        String url = "https://store.steampowered.com/api/appdetails?appids=" + juego.getId();
        String response = restTemplate.getForObject(url, String.class);

        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No se pudo obtener información del juego desde la API de Steam.");
        }


        juegoRepository.save(juego);
    }
}
