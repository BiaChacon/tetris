package com.example.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_tabuleiro.*
import android.view.LayoutInflater
import com.example.tetris.models.*

class TabuleiroActivity : AppCompatActivity() {


    val LINHA = 22
    val COLUNA = 12
    var running = true
    var speed:Long = 300
    var variacao = 1

    var p = gerarPeca()

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
            if(!checarEsquerda() && !bateuLateral())
                p.moveLeft()
        }

        rightBt.setOnClickListener {
            if(!checarDireita() && !bateuLateral())
                p.moveRight()
        }

        downBt.setOnClickListener {
            if(podeDown())
                p.moveDown()
        }

        rotateBt.setOnClickListener {
            if(podeGirar())
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
                                boardView[i][j]!!.setImageResource(R.drawable.branco)
                            }
                        }
                    }

                    //se block bater em peca ou no no final fazer update do tabuleiro e criar novo block
                    if(bateuPeca() || bateuFinal()){
                        updateTabuleiro()
                        fazerBlock()
                        newBlock()
                    }else{
                        p.moveDown()
                    }

                    try {
                        fazerBlock()
                    }catch (e:ArrayIndexOutOfBoundsException) {
                        e.printStackTrace()
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
    }

    fun fazerBlock(){
        boardView[p.pA.x][p.pA.y]!!.setImageResource(R.drawable.rosa)
        boardView[p.pB.x][p.pB.y]!!.setImageResource(R.drawable.rosa)
        boardView[p.pC.x][p.pC.y]!!.setImageResource(R.drawable.rosa)
        boardView[p.pD.x][p.pD.y]!!.setImageResource(R.drawable.rosa)
    }

    fun newBlock(){
        p = gerarPeca()
    }

    fun gerarPeca():Block{

        val random = (1..variacao).shuffled().first()

        /*if(random == 1)
            return Iblock(0,6)
        else if (random == 2)
            return Jblock(0,6)
        else if(random == 3)
            return Lblock(2,6)
        else if(random == 4)
            return Oblock(0,6)
        else if (random == 5)*/
            return Sblock(0,6)
        /*else if (random == 6)
            return Tblock(0,6)
        else
            return Zblock(0,6)*/

    }

    fun bateuPeca():Boolean{
        try {
            if((tabuleiro[p.pA.x+1][p.pA.y] == 1) ||
                (tabuleiro[p.pB.x+1][p.pB.y] == 1) ||
                (tabuleiro[p.pC.x+1][p.pC.y] == 1) ||
                (tabuleiro[p.pD.x+1][p.pD.y] == 1)){
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            e.printStackTrace()
        }
        return false
    }

    fun bateuFinal():Boolean {
        if (p.pA.x+1 >= LINHA ||
            p.pB.x+1 >= LINHA ||
            p.pC.x+1 >= LINHA ||
            p.pD.x+1 >= LINHA){
            return true
        }
        return false
    }


    fun bateuLateral():Boolean{
        if( p.pA.y+1 >= COLUNA ||
            p.pB.y+1 >= COLUNA ||
            p.pC.y+1 >= COLUNA ||
            p.pD.y+1 >= COLUNA){

            return true

        }else if(p.pA.y == 0 ||
                 p.pB.y == 0 ||
                 p.pC.y == 0 ||
                 p.pD.y == 0){

            return true

        }
            return false
    }

    fun checarDireita():Boolean{
        try {
            if((tabuleiro[p.pA.x][p.pA.y+1] == 1) ||
                (tabuleiro[p.pB.x][p.pB.y+1] == 1) ||
                (tabuleiro[p.pC.x][p.pC.y+1] == 1) ||
                (tabuleiro[p.pD.x][p.pD.y+1] == 1)) {
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro direita")
        }
        return false
    }

    fun checarEsquerda():Boolean{
        try {
            if((tabuleiro[p.pA.x][p.pA.y-1] == 1) ||
                (tabuleiro[p.pB.x][p.pB.y-1] == 1) ||
                (tabuleiro[p.pC.x][p.pC.y-1] == 1) ||
                (tabuleiro[p.pD.x][p.pD.y-1] == 1)) {
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro esquerda")
        }
        return false
    }

    fun podeDown():Boolean{
        if (p.pA.x+1 >= LINHA ||
            p.pB.x+1 >= LINHA ||
            p.pC.x+1 >= LINHA ||
            p.pD.x+1 >= LINHA) {
            return false
        }else if((tabuleiro[p.pA.x+1][p.pA.y] == 1) ||
            (tabuleiro[p.pB.x+1][p.pB.y] == 1) ||
            (tabuleiro[p.pC.x+1][p.pC.y] == 1) ||
            (tabuleiro[p.pD.x+1][p.pD.y] == 1)){
            return false
        }
        return true
    }

    fun podeGirar():Boolean{
        if(p.pA.y+p.giro >= COLUNA || p.pA.x+p.giro >= LINHA){
            return false
        }else if (tabuleiro[p.pA.x+p.giro][p.pA.y] == 1 ||
                  tabuleiro[p.pA.x][p.pA.y+p.giro] == 1){
            return false
        }
        return true
    }

    /*fun verificarLinha(){
        var cont=0
        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                if(tabuleiro[i][j] == 1)
                    cont+=1
            }
            if (cont == COLUNA)
                tirar(i)

        }
    }

    fun tirar(linha:Int){

        for (j in 0 until COLUNA) {
            tabuleiro[linha][j] == 0
        }

        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                tabuleiro[i][j] == tabuleiro[i+1][j]

            }
        }

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



    }*/

}