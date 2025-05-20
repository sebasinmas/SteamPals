package com.steampals.SteamPals.repository.UsuarioRepository;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Component;

import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.repository.UsuarioRepository;

@Component
public class DataLoader implements CommandLineRunner {
    private static final Logger logger = Logger.getLogger(DataLoader.class.getName());
    UsuarioRepository usuarioRepository;

    public DataLoader(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

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
                throw new DataAccessResourceFailureException("No sepudieron cargar los usuarios de prueba", e);
            }
            logger.info("Usuarios de prueba cargados en H2: " + usuarios);
        } else {
            throw new Exception("Base de datos ya contiene datos.");
        }
    }

}
