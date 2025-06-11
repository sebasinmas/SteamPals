package com.steampals.steampals.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.steampals.steampals.model.Biblioteca;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BibliotecaRepositoryTest {
    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @BeforeEach // este método se ejecuta antes de cada prueba
    public void setup() {
        // Limpia base por si acaso
        bibliotecaRepository.deleteAll();

        // Crea un grupo de prueba
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId(0L);

        bibliotecaRepository.save(biblioteca);
    }

    @Test
    @Order(1)
    void prueba1(){

    }


}
