package com.aprendizado.jogo_da_velha.modelos;

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
public class Partida {
    @Id
    @GeneratedValue
    private Long id;
    private final Tabuleiro tabuleiro = new Tabuleiro();
    private Jogador jogadorAtual;

    public Partida() {
        var random = new Random();
        this.jogadorAtual = random.nextInt(2) > 0 ? Jogador.X : Jogador.O;
    }
}
