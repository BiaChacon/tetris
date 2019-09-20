package com.example.tetris.models

class Ponto (var x:Int, var y:Int){

    fun moveDown() {
       x++
    }

    fun moveLeft(){
        y--
    }

    fun moveRight(){
        y++
    }

}