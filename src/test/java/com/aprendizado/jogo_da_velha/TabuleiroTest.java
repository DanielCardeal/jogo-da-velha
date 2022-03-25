package com.aprendizado.jogo_da_velha;

import com.aprendizado.jogo_da_velha.modelos.Movimento;
import com.aprendizado.jogo_da_velha.modelos.Tabuleiro;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class TabuleiroTest {
    @Test
    public void testaVitoriaHorizontal() {
        for (int y = 0; y < 3; y++) {
            var tabuleiro = new Tabuleiro();
            tabuleiro.fazMovimento('X', new Movimento(0, y));
            tabuleiro.fazMovimento('X', new Movimento(1, y));
            tabuleiro.fazMovimento('X', new Movimento(2, y));
            Assert.notNull(
                    tabuleiro.getVencedor(),
                    String.format("Não detectou vitória na horizontal para y = %d.", y)
            );
        }
    }

    @Test
    public void testaVitoriaVertical() {
        for (int x = 0; x < 3; x++) {
            var tabuleiro = new Tabuleiro();
            tabuleiro.fazMovimento('X', new Movimento(x, 0));
            tabuleiro.fazMovimento('X', new Movimento(x, 1));
            tabuleiro.fazMovimento('X', new Movimento(x, 2));
            Assert.notNull(
                    tabuleiro.getVencedor(),
                    String.format("Não detectou vitória na vertical para x = %d.", x)
            );
        }
    }

    @Test
    public void testaVitoriaDiagonalPrincipal() {
        var tabuleiro = new Tabuleiro();
        tabuleiro.fazMovimento('X', new Movimento(0, 0));
        tabuleiro.fazMovimento('X', new Movimento(1, 1));
        tabuleiro.fazMovimento('X', new Movimento(2, 2));
        Assert.notNull(
                tabuleiro.getVencedor(),
                "Não detectou vitória na diagonal principal."
        );
    }

    @Test
    public void testaVitoriaDiagonalSecundaria() {
        var tabuleiro = new Tabuleiro();
        tabuleiro.fazMovimento('X', new Movimento(2, 0));
        tabuleiro.fazMovimento('X', new Movimento(1, 1));
        tabuleiro.fazMovimento('X', new Movimento(0, 2));
        Assert.notNull(
                tabuleiro.getVencedor(),
                "Não detectou vitória na diagonal secundária."
        );
    }
}
