package com.aprendizado.jogo_da_velha.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Random;

/**
 * Representa uma partida de jogo da velha.
 */
@Entity
@Data
@JsonIgnoreProperties({"tabuleiro"})
public class Partida {
    public enum ResultadoMov {
        OK,
        JOGADOR_INVAL,
        MOV_INVAL,
        POS_OCUPADA,
        TURNO_ERR,
    }

    @Id
    @GeneratedValue
    private Long id;
    private final Tabuleiro tabuleiro = new Tabuleiro();
    private char jogadorAtual;

    public Partida() {
        var random = new Random();
        this.jogadorAtual = random.nextInt(2) > 0 ? 'X' : 'O';
    }

    public ResultadoMov executaMovimento(Movimento movimento, Character jogador) {
        // Validação movimento
        if (!movimento.valido())
            return ResultadoMov.MOV_INVAL;
        if (tabuleiro.posOcupada(movimento))
            return ResultadoMov.POS_OCUPADA;
        // Validação jogador
        jogador = Character.toUpperCase(jogador);
        if (jogador != 'X' && jogador != 'O')
            return ResultadoMov.JOGADOR_INVAL;
        if (!jogador.equals(jogadorAtual))
            return ResultadoMov.TURNO_ERR;

        tabuleiro.fazMovimento(jogador, movimento);
        proximoJogador();
        return ResultadoMov.OK;
    }

    /**
     * Passa o turno para o próximo jogador
     */
    public void proximoJogador() {
        jogadorAtual = jogadorAtual == 'X'? 'O' : 'X';
    }
}
