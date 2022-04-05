package com.aprendizado.jogo_da_velha.serviços;

import com.aprendizado.jogo_da_velha.modelos.Movimento;
import com.aprendizado.jogo_da_velha.modelos.Partida;
import com.aprendizado.jogo_da_velha.modelos.ResultadoMovimento;
import com.aprendizado.jogo_da_velha.repositorios.RepositorioPartida;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ServicoPartida {
    private final RepositorioPartida repositorioPartida;

    public ServicoPartida(RepositorioPartida repositorioPartida) {
        this.repositorioPartida = repositorioPartida;
    }

    /**
     * Cria instância de partida na base de dados e a devolve.
     */
    public Partida novaPartida() {
        var partida = new Partida();
        return repositorioPartida.save(partida);
    }

    /**
     * Busca e devolve uma partida na base de dados.
     */
    public Optional<Partida> buscaPartida(@NotNull UUID id) {
        return repositorioPartida.findById(id);
    }

    /**
     * Executa o movimento de um dado jogador na partida indicada, devolvendo o seu resultado.
     */
    public ResultadoMovimento executaMovimento(@NotNull Partida partida,
                                               @NotNull Movimento movimento,
                                               @NotNull Character jogador) {
        var resultadoMov = partida.executaMovimento(movimento, jogador);
        if (resultadoMov == ResultadoMovimento.OK || resultadoMov == ResultadoMovimento.FIM_PARTIDA) {
            repositorioPartida.save(partida);
        }
        return resultadoMov;
    }
}
