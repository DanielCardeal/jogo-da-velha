package com.aprendizado.jogo_da_velha.modelos;

import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * Representa o tabuleiro de uma partida de jogo da velha.
 */
@Embeddable
public class Tabuleiro {
    private final char[][] tabuleiro;

    @Getter
    private Status statusVitoria;
    @Getter
    private Character vencedor;

    public enum Status {
        EMPATE, EM_EXECUCAO, VITORIA
    }

    public Tabuleiro() {
        this.tabuleiro = new char[3][3];
        statusVitoria = Status.EM_EXECUCAO;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                this.tabuleiro[i][j] = ' ';
    }

    /**
     * Indica se a posição apontada por um movimento está ocupada.
     */
    public boolean posOcupada(Movimento movimento) {
        var celula = tabuleiro[movimento.getX()][movimento.getY()];
        return celula != ' ';
    }

    /**
     * Marca uma jogada do jogador na posição indicada pelo movimento.
     */
    public void fazMovimento(Character jogador, Movimento movimento) {
        tabuleiro[movimento.getX()][movimento.getY()] = jogador;
        atualizaStatus();
    }

    /**
     * Atualiza o status de completude do tabuleiro.
     */
    private void atualizaStatus() {
        // Colunas
        for (int x = 0; x < 3; x++) {
            if (tabuleiro[x][0] == tabuleiro[x][1] && tabuleiro[x][1] == tabuleiro[x][2] && tabuleiro[x][0] != ' ') {
                setVencedor(tabuleiro[x][0]);
                return;
            }
        }

        // Linhas
        for (int y = 0; y < 3; y++) {
            if (tabuleiro[0][y] == tabuleiro[1][y] && tabuleiro[1][y] == tabuleiro[2][y] && tabuleiro[0][y] != ' ') {
                setVencedor(tabuleiro[0][y]);
                return;
            }
        }

        // Diagonal principal
        if (tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2] && tabuleiro[0][0] != ' ') {
            setVencedor(tabuleiro[0][0]);
            return;
        }

        // Diagonal secundária
        if (tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0] && tabuleiro[0][2] != ' ') {
            setVencedor(tabuleiro[0][2]);
            return;
        }

        // Empate
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (tabuleiro[x][y] == ' ') {
                    statusVitoria = Status.EM_EXECUCAO;
                    return;
                }
            }
        }
        statusVitoria = Status.EMPATE;
    }

    /**
     * Marca o jogador como vencedor da partida
     */
    private void setVencedor(char jogador) {
        statusVitoria = Status.VITORIA;
        vencedor = jogador;
    }
}
