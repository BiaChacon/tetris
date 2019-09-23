package com.example.tetris.models

class Iblock(x:Int, y:Int): Block(x,y){

    init {
        pB = Ponto(x-1,y)
        pC = Ponto(x+1,y)
        pD = Ponto(x+2, y)
        giro = 2
    }

    override fun moveDown() {
        pA.moveDown()
        pB.moveDown()
        pC.moveDown()
        pD.moveDown()
    }

    override fun moveGirar() {
        if (state){
            //trocar ponto B
            pB.x = pA.x
            pB.y = pA.y-1
            //trocar ponto C
            pC.x = pA.x
            pC.y = pA.y+1
            //trocar ponto D
            pD.x = pA.x
            pD.y = pA.y+2
            //mudar state para horizontal
            state = false
        }else{
            //trocar ponto B
            pB.x = pA.x-1
            pB.y = pA.y
            //trocar ponto C
            pC.x = pB.x+1
            pC.y = pB.y
            //trocar ponto D
            pD.x = pB.x+2
            pD.y = pB.y
            //mudar state para vertical
            state = true
        }
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