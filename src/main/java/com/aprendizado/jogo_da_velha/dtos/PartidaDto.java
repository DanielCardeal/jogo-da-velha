package com.aprendizado.jogo_da_velha.dtos;

import com.aprendizado.jogo_da_velha.modelos.Partida;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * DTO usado para devolver informações sobre partidas para o cliente.
 */
@Data
public class PartidaDto {
    @NotNull
    private UUID id;
    @NotNull
    private Character jogadorAtual;

    public PartidaDto(@NotNull Partida partida) {
        this.id = partida.getId();
        this.jogadorAtual = partida.getJogadorAtual();
    }
}
