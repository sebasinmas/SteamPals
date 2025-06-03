package com.steampals.steampals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessResourceFailureException;

import com.steampals.steampals.DataLoader;
import com.steampals.steampals.DataLoader;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.repository.UsuarioRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DataLoaderTest {
    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    DataLoader dataLoader;

    public DataLoaderTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarUsuariosCuandoLaDbEstaVacia() throws Exception {
        // Arrange
        when(usuarioRepository.count()).thenReturn(0L);
        when(usuarioRepository.saveAll(anyList())).thenReturn(List.of(
                Usuario.builder().usuario("steampals").build(),
                Usuario.builder().usuario("pepito@gmail.com").build()));

        // Act
        dataLoader.run();

        // Assert
        verify(usuarioRepository, times(1)).saveAll(anyList());
    }

    @Test
    void dbConDatos() {
        // Arrange
        when(usuarioRepository.count()).thenReturn(5L);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            dataLoader.run();
        });
        assertEquals("Base de datos ya contiene datos.", exception.getMessage());

        verify(usuarioRepository, never()).saveAll(anyList());
    }

    @Test
    void seDebeLanzarUnaExcepcionCuandoFallaElGuardado() {
        // Arrange
        when(usuarioRepository.count()).thenReturn(0L);
        doThrow(new RuntimeException("DB error")).when(usuarioRepository).saveAll(anyList());

        // Act & Assert
        DataAccessResourceFailureException exception = assertThrows(DataAccessResourceFailureException.class, () -> {
            dataLoader.run();
        });

        assertTrue(exception.getMessage().contains("No sepudieron cargar los usuarios de prueba"));
        verify(usuarioRepository, times(1)).saveAll(anyList());
    }
}
