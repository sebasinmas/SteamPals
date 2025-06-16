package com.steampals.steampals.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.steampals.steampals.dto.LoginDTO;
import com.steampals.steampals.dto.RegistroUsuarioDTO;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.model.Usuario.Rol;
import com.steampals.steampals.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(RegistroUsuarioDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .nombreUsuario(dto.getNombreUsuario())
                .email(dto.getEmail())
                .contrasenia(passwordEncoder.encode(dto.getContrasenia()))
                .edad(dto.getEdad())
                .pais(dto.getPais())
                .fechaDeRegistro(java.time.LocalDate.now())
                .rol(Rol.USUARIO)
                .build();
        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario login(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(dto.getContrasenia(), usuario.getContrasenia())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return usuario; // En el futuro podríamos devolver un token JWT o algo similar
    }
}
