package com.aprendizado.jogo_da_velha.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Random;
import java.util.UUID;

/**
 * Representa uma partida de jogo da velha.
 */
@Entity
@Data
@JsonIgnoreProperties({"tabuleiro", "status", "vencedor"})
public class Partida {
    @Id
    @GeneratedValue
    private UUID id;
    private final Tabuleiro tabuleiro = new Tabuleiro();
    private char jogadorAtual;
    private StatusPartida status;

    public Partida() {
        var random = new Random();
        this.jogadorAtual = random.nextInt(2) > 0 ? 'X' : 'O';
        this.status = StatusPartida.EM_EXECUCAO;
    }

    public ResultadoMovimento executaMovimento(Movimento movimento, Character jogador) {
        // Impede modificação de partidas finalizadas
        if (status == StatusPartida.FINALIZADA)
            return ResultadoMovimento.FIM_PARTIDA;
        // Validação movimento
        if (!movimento.valido())
            return ResultadoMovimento.MOV_INVAL;
        if (tabuleiro.posOcupada(movimento))
            return ResultadoMovimento.POS_OCUPADA;
        // Validação jogador
        jogador = Character.toUpperCase(jogador);
        if (jogador != 'X' && jogador != 'O')
            return ResultadoMovimento.JOGADOR_INVAL;
        if (!jogador.equals(jogadorAtual))
            return ResultadoMovimento.TURNO_ERR;

        tabuleiro.fazMovimento(jogador, movimento);
        proximoJogador();

        // Checa fim de partida
        if (tabuleiro.getStatusVitoria() != StatusTabuleiro.EM_EXECUCAO) {
            status = StatusPartida.FINALIZADA;
            return ResultadoMovimento.FIM_PARTIDA;
        }

        return ResultadoMovimento.OK;
    }

    /**
     * Devolve o jogador que venceu a partida. Caso a partida tenha finalizado em empate, devolve {@code null}
     */
    @Nullable
    public Character getVencedor() {
        if (tabuleiro.getStatusVitoria() == StatusTabuleiro.EMPATE)
            return null;
        else
            return tabuleiro.getVencedor();
    }

    /**
     * Passa o turno para o próximo jogador
     */
    public void proximoJogador() {
        jogadorAtual = jogadorAtual == 'X' ? 'O' : 'X';
    }
}
