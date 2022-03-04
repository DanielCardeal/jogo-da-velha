package com.aprendizado.jogo_da_velha.controladores;

import com.aprendizado.jogo_da_velha.modelos.Partida;
import com.aprendizado.jogo_da_velha.repositorios.RepositorioPartida;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorPartida {
    private final RepositorioPartida repositorio;

    public ControladorPartida(RepositorioPartida repositorio) {
        this.repositorio = repositorio;
    }

    @PostMapping("/game")
    public Partida novaPartida() {
        var partida = new Partida();
        return repositorio.save(partida);
    }
}
