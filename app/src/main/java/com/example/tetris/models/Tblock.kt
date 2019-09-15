package com.example.tetris

class Tblock(x:Int, y:Int):Block(x,y){

    init {
        pB = Ponto(-1, -1)
        pC = Ponto(0,-1)
        pD = Ponto(1,-1)
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
