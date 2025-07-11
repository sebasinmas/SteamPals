package com.steampals.steampals.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.steampals.steampals.model.MatchUsuario;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.repository.MatchUsuarioRepository;
import com.steampals.steampals.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchUsuarioRepository matchUsuarioRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerPerfilesParaUsuario(Usuario actual) {
        List<Usuario> yaVistos = new ArrayList<>();
        yaVistos.addAll(matchUsuarioRepository.findUsuariosDondeEsUsuario1(actual));
        yaVistos.addAll(matchUsuarioRepository.findUsuariosDondeEsUsuario2(actual));
        yaVistos.add(actual);
        return usuarioRepository.findByIdNotIn(yaVistos.stream().map(Usuario::getId).toList());
    }

    public void darLike(Usuario actual, Usuario objetivo) {
        Optional<MatchUsuario> matchOpt = matchUsuarioRepository.findMatchBetween(actual, objetivo);

        if (matchOpt.isPresent()) {
            MatchUsuario match = matchOpt.get();
            if (match.getUsuario1().equals(actual)) {
                match.setUsuario1Like(true);
            } else {
                match.setUsuario2Like(true);
            }
            if (match.isUsuario1Like() && match.isUsuario2Like()) {
                match.setCompleto(true);
            }
            matchUsuarioRepository.save(match);
        } else {
            MatchUsuario nuevo = new MatchUsuario();
            nuevo.setUsuario1(actual);
            nuevo.setUsuario2(objetivo);
            nuevo.setUsuario1Like(true);
            nuevo.setUsuario2Like(false);
            nuevo.setCompleto(false);
            matchUsuarioRepository.save(nuevo);
        }
    }
}

