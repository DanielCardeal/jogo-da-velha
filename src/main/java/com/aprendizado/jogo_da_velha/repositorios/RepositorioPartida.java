package com.aprendizado.jogo_da_velha.repositorios;

import com.aprendizado.jogo_da_velha.modelos.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioPartida extends JpaRepository<Partida, Long> { }
