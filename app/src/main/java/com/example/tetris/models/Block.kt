package com.example.tetris.models

abstract class Block(var x:Int, var y:Int){

    var pA = Ponto(x,y)
    lateinit var pB:Ponto
    lateinit var pC:Ponto
    lateinit var pD:Ponto

    var state = true

    abstract fun moveGirar()
    abstract fun moveDown()
    abstract fun moveLeft()
    abstract fun moveRight()

}