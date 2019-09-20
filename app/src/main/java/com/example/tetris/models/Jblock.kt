package com.example.tetris.models

class Jblock(x:Int, y:Int):Block(x,y){

    init {
        pB = Ponto(x+1,y)
        pC = Ponto(x+2,y)
        pD = Ponto(x+2,y-1)
    }

    override fun moveDown() {
        pA.moveDown()
        pB.moveDown()
        pC.moveDown()
        pD.moveDown()
    }

    override fun moveGirar() {
        //Ponto B pivo
        if (state){
            //trocar ponto A
            pA.x = pB.x
            pA.y = pB.y+1
            //trocar ponto C
            pC.x = pB.x
            pC.y = pB.y-1
            //trocar ponto D
            pD.x = pB.x-1
            pD.y = pB.y-1
            //mudar state para horizontal
            state = false
        }else{
            //trocar ponto A
            pA.x = pB.x-1
            pA.y = pB.y
            //trocar ponto C
            pC.x = pB.x+1
            pC.y = pB.y
            //trocar ponto D
            pD.x = pB.x+1
            pD.y = pB.y-1
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
