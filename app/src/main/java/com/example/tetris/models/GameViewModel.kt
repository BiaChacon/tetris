package com.example.tetris.models

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel(){

    val LINHA = 22
    val COLUNA = 12

    var tabuleiro = Array(LINHA) {
        Array(COLUNA){0}
    }

}