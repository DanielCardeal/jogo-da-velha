package com.aprendizado.jogo_da_velha.modelos;

import javax.persistence.Embeddable;

/**
 * Representa o tabuleiro de uma partida de jogo da velha.
 */
@Embeddable
public class Tabuleiro {
    private final Jogador[][] tabuleiro;

    public Tabuleiro() {
        this.tabuleiro = new Jogador[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                this.tabuleiro[i][j] = Jogador.VAZIO;
    }
}
