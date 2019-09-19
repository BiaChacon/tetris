package com.example.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_tabuleiro.*
import android.view.LayoutInflater

class TabuleiroActivity : AppCompatActivity() {


    val LINHA = 22
    val COLUNA = 12
    var running = true
    var speed:Long = 300

    var pt = Lblock(2,6)

    //val board = Array(LINHA, { IntArray(COLUNA) })

    var board = Array(LINHA) {
        Array(COLUNA){0}
    }

    var boardView = Array(LINHA){
        arrayOfNulls<ImageView>(COLUNA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabuleiro)

        gridboard.rowCount = LINHA
        gridboard.columnCount = COLUNA

        val inflater = LayoutInflater.from(this)

        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                boardView[i][j] = inflater.inflate(R.layout.inflate_image_view, gridboard, false) as ImageView
                gridboard.addView( boardView[i][j])
            }
        }

        leftBt.setOnClickListener {
            if(!bateuEsquerda()){
                pt.moveLeft()
            }
        }

        rightBt.setOnClickListener {
            if(!bateuDireita()){
                pt.moveRight()
            }
        }

        downBt.setOnClickListener {
            pt.moveDown()
        }
        rotateBt.setOnClickListener {
            pt.moveGirar()
        }

        gameRun()
    }

    fun gameRun(){
        Thread{
            while(running){
                Thread.sleep(speed)
                runOnUiThread{
                    //limpa tela
                    for (i in 0 until LINHA) {
                        for (j in 0 until COLUNA) {
                            if (board[i][j] == 0){
                                boardView[i][j]!!.setImageResource(R.drawable.branco)
                            }
                        }
                    }
                    //move peça atual
                    if(!bateuPeca()){
                        pt.moveDown()
                    }

                    //print peça
                    try {
                        fazerBlock()
                    }catch (e:ArrayIndexOutOfBoundsException) {
                        //se a peça passou das bordas eu vou parar o jogo
                        running = false
                        bordaOuLateral()
                    }

                }
            }
        }.start()
    }


    fun update(){

        board[pt.pA.x][pt.pA.y] =1
        board[pt.pB.x][pt.pB.y] =1
        board[pt.pC.x][pt.pC.y] =1
        board[pt.pD.x][pt.pD.y] =1

        fazerBlock()
        newBlock()

    }

    fun fazerBlock(){

        boardView[pt.pA.x][pt.pA.y]!!.setImageResource(R.drawable.roxo)
        boardView[pt.pB.x][pt.pB.y]!!.setImageResource(R.drawable.roxo)
        boardView[pt.pC.x][pt.pC.y]!!.setImageResource(R.drawable.roxo)
        boardView[pt.pD.x][pt.pD.y]!!.setImageResource(R.drawable.roxo)

    }

    fun newBlock(){
        pt = Lblock(2,6)
    }

    fun bateuPeca():Boolean{
        try {
            if((board[pt.pA.x+1][pt.pA.y] == 1) || (board[pt.pB.x+1][pt.pB.y] == 1)//bateu no final da peca
                || (board[pt.pC.x+1][pt.pC.y] == 1) || (board[pt.pD.x+1][pt.pD.y] == 1)){
                //bateuFinal()
                update()
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro não importante")
        }
        return false
    }

    //bateu no final ou na lateral?
    fun bordaOuLateral(){
        if(pt.pA.x >= LINHA || pt.pB.x >= LINHA || pt.pC.x >= LINHA ||
            pt.pD.x >= LINHA){
            bateuFinal()
        }else{
            bateuLateral()
        }
    }

    fun bateuFinal(){
        pt.pA.x-=1
        pt.pB.x-=1
        pt.pC.x-=1
        pt.pD.x-=1
        update()
    }

    //ver se bateu na direita ou esquerda
    fun bateuLateral(){
        if(pt.pA.y >= COLUNA || pt.pB.y >= COLUNA || pt.pC.y >= COLUNA ||
            pt.pD.y >= COLUNA){
            pt.moveLeft()
        }else{
            pt.moveRight()
        }
    }

    fun bateuDireita():Boolean{
        try {
            if((board[pt.pA.x][pt.pA.y+2] == 1) || (board[pt.pB.x][pt.pB.y+2] == 1)
                || (board[pt.pC.x][pt.pC.y+2] == 1) || (board[pt.pD.x][pt.pD.y+2] == 1)) {//bateu no lado direito
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro não importante")
        }
        return false
    }

    fun bateuEsquerda():Boolean{
        try {
            if((board[pt.pA.x][pt.pA.y-1] == 1) || (board[pt.pB.x][pt.pB.y-1] == 1)
                || (board[pt.pC.x][pt.pC.y-1] == 1) || (board[pt.pD.x][pt.pD.y-1] == 1)) {//bateu no lado esquerdo
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro não importante")
        }
        return false
    }

}
