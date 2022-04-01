package com.aprendizado.jogo_da_velha.controladores;

import com.aprendizado.jogo_da_velha.modelos.Movimento;
import com.aprendizado.jogo_da_velha.modelos.Partida;
import com.aprendizado.jogo_da_velha.modelos.ResultadoMovimento;
import com.aprendizado.jogo_da_velha.serviços.ServicoPartida;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class ControladorPartida {
    private final ServicoPartida servicoPartida;

    public ControladorPartida(ServicoPartida servicoPartida) {
        this.servicoPartida = servicoPartida;
    }

    /**
     * Endpoint para criação de novas partidas.
     */
    @PostMapping
    public Partida novaPartida() {
        return servicoPartida.novaPartida();
    }

    /**
     * Endpoint usado para fazer uma jogada num determinado tabuleiro.
     */
    @PostMapping("/{id}/movement")
    public HashMap<String, String> movimento(@PathVariable UUID id,
                                             @RequestBody MovimentoRequisicao movimentoRequisicao) {
        HashMap<String, String> resposta = new HashMap<>();
        Optional<Partida> opcionalPartida = servicoPartida.buscaPartida(id);
        if (opcionalPartida.isEmpty()) {
            resposta.put("msg", "Partida não encontrada");
            return resposta;
        }

        Partida partida = opcionalPartida.get();
        Movimento movimento = movimentoRequisicao.getPosicao();
        Character jogador = movimentoRequisicao.getJogador();
        ResultadoMovimento resultadoMov = servicoPartida.executaMovimento(partida, movimento, jogador);
        switch (resultadoMov) {
            case OK:
                break;
            case FIM_PARTIDA:
                Character vencedor = partida.getVencedor();
                String textoVencedor = vencedor == null ? "Draw" : vencedor.toString().toUpperCase();
                resposta.put("msg", "Partida finalizada");
                resposta.put("winner", textoVencedor);
                break;
            case JOGADOR_INVAL:
                resposta.put("msg", "Jogador inválido");
                break;
            case MOV_INVAL:
                resposta.put("msg", "Movimento fora dos limites do tabuleiro");
                break;
            case POS_OCUPADA:
                resposta.put("msg", "Posição já ocupada no tabuleiro");
                break;
            case TURNO_ERR:
                resposta.put("msg", "Não é turno do jogador");
                break;
        }

        return resposta;
    }
}

@Data
class MovimentoRequisicao {
    @JsonProperty("player")
    private Character jogador;
    @JsonProperty("position")
    private Movimento posicao;
}