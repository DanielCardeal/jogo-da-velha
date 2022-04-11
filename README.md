# Jogo da Velha

## O projeto

Esse repositório implementa uma API simples para criação e manipulação de partidas de jogo da velha.

O código pode ser compilado e executado usando qualquer IDE moderna para Java (testado no IntelliJ Idea). 

Esse projeto é apenas para aprendizado próprio 😄 

## Endpoints

A API usa dois endpois de POST para implementar a lógica do programa:

- /game: cria uma nova partida com um tabuleiro vazio e um jogador inicial

- /game/{id}/movement: realiza um movimento para o tabuleiro indicado por *id*.  Deve receber os parametros *player* (string 'x' ou 'o'), *position* (dicionário com chaves 'x' e 'y' com as coordenadas do tabuleiro) e *id*.
