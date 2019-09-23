package com.example.tetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_tabuleiro.*
import android.view.LayoutInflater
import com.example.tetris.models.*

class TabuleiroActivity : AppCompatActivity() {

    var running = true
    var game = Game()

    var p = gerarPeca()

    var boardView = Array(game.LINHA){
        arrayOfNulls<ImageView>(game.COLUNA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabuleiro)

        gridboard.rowCount = game.LINHA
        gridboard.columnCount = game.COLUNA

        val inflater = LayoutInflater.from(this)

        for (i in 0 until game.LINHA) {
            for (j in 0 until game.COLUNA) {
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
            girar()
        }

        gameRun()
    }

    fun gameRun(){
        Thread{
            while(running){
                Thread.sleep(game.speed)
                runOnUiThread{
                    //limpa tela
                    for (i in 0 until game.LINHA) {
                        for (j in 0 until game.COLUNA) {
                            if (game.tabuleiro[i][j] == 0){
                                boardView[i][j]!!.setImageResource(R.drawable.branco)
                            }
                        }
                    }

                    //verificar se perdeu
                    perdeu()

                    //mostrar pontos
                    pontAtualText.text = game.pontos.toString()

                    //se block bater em peca ou no no final fazer update do tabuleiro e criar novo block
                    if(bateuPeca() || bateuFinal()){
                        updateTabuleiro()
                        verificarPonto()
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
        game.tabuleiro[p.pA.x][p.pA.y] =1
        game.tabuleiro[p.pB.x][p.pB.y] =1
        game.tabuleiro[p.pC.x][p.pC.y] =1
        game.tabuleiro[p.pD.x][p.pD.y] =1
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

        val random = (1..game.variacao).shuffled().first()

        if(random == 1)
            return Iblock(1,6)
        else if (random == 2)
            return Jblock(2,6)
        else if(random == 3)
            return Lblock(2,6)
        else if(random == 4)
            return Oblock(0,6)
        else if (random == 5)
            return Sblock(1,6)
        else if (random == 6)
            return Tblock(0,6)
        else
            return Zblock(0,6)

    }

    fun bateuPeca():Boolean{
        try {
            if((game.tabuleiro[p.pA.x+1][p.pA.y] == 1) ||
                (game.tabuleiro[p.pB.x+1][p.pB.y] == 1) ||
                (game.tabuleiro[p.pC.x+1][p.pC.y] == 1) ||
                (game.tabuleiro[p.pD.x+1][p.pD.y] == 1)){
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            e.printStackTrace()
        }
        return false
    }

    fun bateuFinal():Boolean {
        if (p.pA.x+1 >= game.LINHA ||
            p.pB.x+1 >= game.LINHA ||
            p.pC.x+1 >= game.LINHA ||
            p.pD.x+1 >= game.LINHA){
            return true
        }
        return false
    }

    fun bateuLateral():Boolean{
        if( p.pA.y+1 >= game.COLUNA ||
            p.pB.y+1 >= game.COLUNA ||
            p.pC.y+1 >= game.COLUNA ||
            p.pD.y+1 >= game.COLUNA){

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
            if((game.tabuleiro[p.pA.x][p.pA.y+1] == 1) ||
                (game.tabuleiro[p.pB.x][p.pB.y+1] == 1) ||
                (game.tabuleiro[p.pC.x][p.pC.y+1] == 1) ||
                (game.tabuleiro[p.pD.x][p.pD.y+1] == 1)) {
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro direita")
        }
        return false
    }

    fun checarEsquerda():Boolean{
        try {
            if((game.tabuleiro[p.pA.x][p.pA.y-1] == 1) ||
                (game.tabuleiro[p.pB.x][p.pB.y-1] == 1) ||
                (game.tabuleiro[p.pC.x][p.pC.y-1] == 1) ||
                (game.tabuleiro[p.pD.x][p.pD.y-1] == 1)) {
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro esquerda")
        }
        return false
    }

    fun podeDown():Boolean{
        if (p.pA.x+1 >= game.LINHA ||
            p.pB.x+1 >= game.LINHA ||
            p.pC.x+1 >= game.LINHA ||
            p.pD.x+1 >= game.LINHA) {
            return false
        }else if((game.tabuleiro[p.pA.x+1][p.pA.y] == 1) ||
            (game.tabuleiro[p.pB.x+1][p.pB.y] == 1) ||
            (game.tabuleiro[p.pC.x+1][p.pC.y] == 1) ||
            (game.tabuleiro[p.pD.x+1][p.pD.y] == 1)){
            return false
        }
        return true
    }

    fun verificarPonto(){
        for (i in 0 until game.LINHA) {
            if (game.tabuleiro[i].sum() == 12){
                tirar(i)
                game.pontos +=1
            }
        }
    }

    fun tirar(linha:Int){
        for (i in linha downTo  1) {
            game.tabuleiro[i] = game.tabuleiro[i-1]
        }

    }

    fun girar(){

        //salvar posicao
        var pontoA = Ponto(p.pA.x, p.pA.y)
        var pontoB = Ponto(p.pB.x, p.pB.y)
        var pontoC = Ponto(p.pC.x, p.pC.y)
        var pontoD = Ponto(p.pD.x, p.pD.y)

        //achar lugar para girar
        while (p.pA.y < p.giro || p.pA.y > (game.COLUNA-1) - p.giro){
                    if (p.pA.y<p.giro){
                        p.moveRight()
                    }else{
                        p.moveLeft()
                    }
            }

        //ver se tem espaco em baixo para rodar
        if (p.pA.x < game.LINHA - p.giro) {
            p.moveGirar()
        }

        //verificar se bate em alguma peca e se bater voltar a ser o que era antes
        if ((game.tabuleiro[p.pA.x][p.pA.y] == 1) ||
                (game.tabuleiro[p.pB.x][p.pB.y] == 1) ||
                (game.tabuleiro[p.pC.x][p.pC.y] == 1) ||
                (game.tabuleiro[p.pD.x][p.pD.y] == 1)){

                   p.pA = Ponto(pontoA.x, pontoA.y)
                   p.pB = Ponto(pontoB.x, pontoB.y)
                   p.pC = Ponto(pontoC.x, pontoC.y)
                   p.pD = Ponto(pontoD.x, pontoD.y)

        }

    }

    fun bateuCima():Boolean{
        if (p.pA.x == 0 ||
            p.pB.x == 0 ||
            p.pC.x == 0 ||
            p.pD.x == 0) {
            return true
        }
        return false
    }

    fun perdeu(){
        if(bateuPeca() && bateuCima()){

            running = false

            var i = Intent(this,ResultActivity::class.java)
            var b = Bundle()

            b.putInt("pontuacao", game.pontos)
            i.putExtras(b)

            startActivity(i)
            finish()
        }
    }

}