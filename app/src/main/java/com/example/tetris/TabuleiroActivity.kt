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

    var p = Lblock(2,6)

    var tabuleiro = Array(LINHA) {
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
            if(!checarEsquerda()){
                p.moveLeft()
            }
        }

        rightBt.setOnClickListener {
            if(!checarDireita()){
                p.moveRight()
            }
        }

        downBt.setOnClickListener {
            p.moveDown()
        }
        rotateBt.setOnClickListener {
            p.moveGirar()
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
                            if (tabuleiro[i][j] == 0){
                                boardView[i][j]!!.setImageResource(R.drawable.white)
                            }
                        }
                    }
                    //move peça atual
                    if(!bateuPeca()){
                        p.moveDown()
                    }

                    //print peça
                    try {
                        fazerBlock()
                    }catch (e:ArrayIndexOutOfBoundsException) {
                        //se a peça passou das bordas eu vou parar o jogo
                        running = false
                        verificarBateu()
                    }

                }
            }
        }.start()
    }


    fun updateTabuleiro(){

        tabuleiro[p.pA.x][p.pA.y] =1
        tabuleiro[p.pB.x][p.pB.y] =1
        tabuleiro[p.pC.x][p.pC.y] =1
        tabuleiro[p.pD.x][p.pD.y] =1

        fazerBlock()
        newBlock()

    }

    fun fazerBlock(){

        boardView[p.pA.x][p.pA.y]!!.setImageResource(R.drawable.green)
        boardView[p.pB.x][p.pB.y]!!.setImageResource(R.drawable.green)
        boardView[p.pC.x][p.pC.y]!!.setImageResource(R.drawable.green)
        boardView[p.pD.x][p.pD.y]!!.setImageResource(R.drawable.green)

    }

    fun newBlock(){
        p = Lblock(2,6)
    }

    fun bateuPeca():Boolean{
        try {
            if((tabuleiro[p.pA.x+1][p.pA.y] == 1) ||
                (tabuleiro[p.pB.x+1][p.pB.y] == 1) ||
                (tabuleiro[p.pC.x+1][p.pC.y] == 1) ||
                (tabuleiro[p.pD.x+1][p.pD.y] == 1)){
                //bateuFinal()
                updateTabuleiro()
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro não importante")
        }
        return false
    }

    fun verificarBateu(){
        if(p.pA.x >= LINHA ||
            p.pB.x >= LINHA ||
            p.pC.x >= LINHA ||
            p.pD.x >= LINHA){

            bateuFinal()

        }else{

            bateuLateral()

        }

    }

    fun bateuFinal(){
        p.pA.x-=1
        p.pB.x-=1
        p.pC.x-=1
        p.pD.x-=1
        updateTabuleiro()
    }

    fun bateuLateral(){
        if( p.pA.y >= COLUNA ||
            p.pB.y >= COLUNA ||
            p.pC.y >= COLUNA ||
            p.pD.y >= COLUNA){

            p.moveLeft()

        }else{

            p.moveRight()

        }

    }

    fun checarDireita():Boolean{
        try {
            if((tabuleiro[p.pA.x][p.pA.y+2] == 1) ||
                (tabuleiro[p.pB.x][p.pB.y+2] == 1) ||
                (tabuleiro[p.pC.x][p.pC.y+2] == 1) ||
                (tabuleiro[p.pD.x][p.pD.y+2] == 1)) {
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro não importante")
        }
        return false
    }

    fun checarEsquerda():Boolean{
        try {
            if((tabuleiro[p.pA.x][p.pA.y-1] == 1) ||
                (tabuleiro[p.pB.x][p.pB.y-1] == 1)
                || (tabuleiro[p.pC.x][p.pC.y-1] == 1) ||
                (tabuleiro[p.pD.x][p.pD.y-1] == 1)) {
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro não importante")
        }
        return false
    }

}
