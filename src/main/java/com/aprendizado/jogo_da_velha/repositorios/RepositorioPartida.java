package com.aprendizado.jogo_da_velha.repositorios;

import com.aprendizado.jogo_da_velha.modelos.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositorioPartida extends JpaRepository<Partida, UUID> {
}
