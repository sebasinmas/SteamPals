package com.steampals.steampals;

import java.util.List;

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
            List<Usuario> usuarios = List.of(
                    Usuario.builder()
                            .usuario("steampals")
                            .email("admin@pals.cl").contrasenia("admin").build(),
                    Usuario.builder()
                            .usuario("pepito@gmail.com")
                            .contrasenia("pepito")
                            .edad(18)
                            .pais("Chile")
                            .descripcion("Hola soy Pepito").build());
            try {
                usuarioRepository.saveAll(usuarios);
            } catch (Exception e) {
                throw new Exception("Error al cargar los datos de prueba en H2: " + e.getMessage());
            }
            System.out.println("Usuario de prueba cargado en H2.");
        } else {
            throw new Exception("Base de datos ya contiene datos.");
        }
    }

}
