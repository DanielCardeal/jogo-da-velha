package com.aprendizado.jogo_da_velha.modelos;

/**
 * Possíveis resultados obtidos ao tentar fazer um movimento numa partida.
 */
public enum ResultadoMovimento {
    OK,
    FIM_PARTIDA,
    JOGADOR_INVAL,
    MOV_INVAL,
    POS_OCUPADA,
    TURNO_ERR,
}
