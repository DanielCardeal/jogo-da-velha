package com.aprendizado.jogo_da_velha.modelos;

import lombok.Data;

/**
 * Coordenadas do tabuleiro que indicam a posição de uma jogada.
 */
@Data
public class Movimento {
    private int x, y;

    /**
     * Verifica se o movimento é válido, ou seja, se ambas as coordenadas estão no intervalo [0..2].
     */
    public boolean valido() {
        return x >= 0 && x < 3 && y >= 0 && y < 3;
    }
}
