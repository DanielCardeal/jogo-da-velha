package com.aprendizado.jogo_da_velha.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Random;

/**
 * Representa uma partida de jogo da velha.
 */
@Entity
@Data
@JsonIgnoreProperties({"tabuleiro", "status", "vencedor"})
public class Partida {
    public enum Status {
        FINALIZADA, EM_EXECUCAO
    }

    public enum ResultadoMov {
        OK,
        FIM_PARTIDA,
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

    private Status status;

    public Partida() {
        var random = new Random();
        this.jogadorAtual = random.nextInt(2) > 0 ? 'X' : 'O';
        this.status = Status.EM_EXECUCAO;
    }

    public ResultadoMov executaMovimento(Movimento movimento, Character jogador) {
        // Impede modificação de partidas finalizadas
        if (status == Status.FINALIZADA)
            return ResultadoMov.FIM_PARTIDA;
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

        // Checa fim de partida
        if (tabuleiro.getStatusVitoria() != Tabuleiro.Status.EM_EXECUCAO) {
            status = Status.FINALIZADA;
            return ResultadoMov.FIM_PARTIDA;
        }

        return ResultadoMov.OK;
    }

    /**
     * Devolve o jogador que venceu a partida. Caso a partida tenha finalizado em empate, devolve {@code null}
     */
    @Nullable
    public Character getVencedor() {
        if (tabuleiro.getStatusVitoria() == Tabuleiro.Status.EMPATE)
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
