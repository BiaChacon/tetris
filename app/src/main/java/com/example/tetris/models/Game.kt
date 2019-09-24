package com.example.tetris.models

import androidx.lifecycle.ViewModel

class Game : ViewModel(){

    val LINHA = 22
    val COLUNA = 12

    var speed = longArrayOf(600,300,100)

    var tabuleiro = Array(LINHA) {
        Array(COLUNA){0}
    }

    var variacao = 7
    var pontos = 0
    var record = 0






}