package com.aprendizado.jogo_da_velha.modelos;

import javax.persistence.Embeddable;

/**
 * Representa o tabuleiro de uma partida de jogo da velha.
 */
@Embeddable
public class Tabuleiro {
    private final char[][] tabuleiro;

    public Tabuleiro() {
        this.tabuleiro = new char[3][3];
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
    }
}
