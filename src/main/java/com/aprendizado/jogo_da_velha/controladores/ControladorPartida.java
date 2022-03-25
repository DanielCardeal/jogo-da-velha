package com.aprendizado.jogo_da_velha.controladores;

import com.aprendizado.jogo_da_velha.modelos.Movimento;
import com.aprendizado.jogo_da_velha.modelos.Partida;
import com.aprendizado.jogo_da_velha.modelos.ResultadoMovimento;
import com.aprendizado.jogo_da_velha.repositorios.RepositorioPartida;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/game")
public class ControladorPartida {
    private final RepositorioPartida repositorio;

    public ControladorPartida(RepositorioPartida repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Cria instância de partida na base de dados e a devolve.
     */
    @PostMapping
    public Partida novaPartida() {
        var partida = new Partida();
        return repositorio.save(partida);
    }

    /**
     * Endpoint usado para fazer uma jogada em um determinado tabuleiro.
     */
    @PostMapping("/{id}/movement")
    public HashMap<String, String> movimento(@RequestBody MovimentoRequisicao movimento) {
        HashMap<String, String> resposta = new HashMap<>();
        // Procura a partida no banco de dados
        Optional<Partida> partida = repositorio.findById(movimento.getId());
        if (partida.isEmpty()) {
            resposta.put("msg", "Partida não encontrada");
            return resposta;
        }

        var resultadoMov = partida.map((p) -> p.executaMovimento(movimento.getPosicao(), movimento.getJogador()));
        switch (resultadoMov.orElse(ResultadoMovimento.OK)) {
            case OK:
                partida.map(repositorio::save);
                break;
            case FIM_PARTIDA:
                partida.map(repositorio::save);
                Optional<String> vencedor = partida.map(p -> {
                    var v = p.getVencedor();
                    if (v == null) {
                        return "Draw";
                    }
                    return v.toString().toUpperCase();
                });
                resposta.put("msg", "Partida finalizada");
                resposta.put("winner", vencedor.get());
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
    private Long id;
    @JsonProperty("player")
    private Character jogador;
    @JsonProperty("position")
    private Movimento posicao;
}