package com.example.tetris.models

import com.example.tetris.Block
import com.example.tetris.Ponto


class Iblock(x:Int, y:Int): Block(x,y){

    init {
        pB = Ponto(1, 0)
        pC = Ponto(2, 0)
        pD = Ponto(3, 0)
    }

    override fun moveDown() {
        pA.moveDown()
        pB.moveDown()
        pC.moveDown()
        pD.moveDown()
    }

    override fun moveGirar() {

    }

    override fun moveLeft() {
        pA.moveLeft()
        pB.moveLeft()
        pC.moveLeft()
        pD.moveLeft()
    }

    override fun moveRight() {
        pA.moveRight()
        pB.moveRight()
        pC.moveRight()
        pD.moveRight()
    }

}