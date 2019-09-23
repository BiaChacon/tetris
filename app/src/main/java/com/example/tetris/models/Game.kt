package com.example.tetris.models

class Game {

    val LINHA = 22
    val COLUNA = 12

    var tabuleiro = Array(LINHA) {
        Array(COLUNA){0}
    }

    var variacao = 7
    var pontos = 0
    var record = 0
    var speed:Long = 300





}