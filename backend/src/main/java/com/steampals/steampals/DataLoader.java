package com.steampals.SteamPals.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.repository.UsuarioRepository;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario usuario = new Usuario();
            usuario.setUsuario("Kevin");
            usuario.setEmail("kevin@email.com");
            usuarioRepository.save(usuario);

            System.out.println("🟢 Usuario de prueba cargado en H2.");
        }else {
            throw new Exception("🔴 Base de datos ya contiene datos.");
        }
    }

}
