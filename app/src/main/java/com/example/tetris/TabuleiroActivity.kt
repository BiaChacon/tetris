package com.example.tetris

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_tabuleiro.*
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.tetris.models.*
import kotlin.random.Random

class TabuleiroActivity : AppCompatActivity() {

    val LINHA = 22
    val COLUNA = 12

    var running = true
    //var game = GameViewModel()

    // [ NORMAL | FACIL | DIFICIL ]
    var speed = longArrayOf(250,600,100)

    var p = gerarPeca()

    var pontos = 0
    var escolhido = 0

    var pause = 0

    val game: GameViewModel by lazy {
        ViewModelProviders.of(this)[GameViewModel::class.java]
    }

    var boardView = Array(LINHA){
        arrayOfNulls<ImageView>(COLUNA)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabuleiro)

        //pegar prefs
        val settings = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        escolhido = settings.getInt("dificuldade", 0)


        //tamanho do gridboard
        gridboard.rowCount = LINHA
        gridboard.columnCount = COLUNA

        val inflater = LayoutInflater.from(this)

        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                boardView[i][j] = inflater.inflate(R.layout.inflate_image_view, gridboard, false) as ImageView
                gridboard.addView( boardView[i][j])
            }
        }

        gameRun()

        leftBt.setOnClickListener {
            if(!checarEsquerda())
                p.moveLeft()
        }

        rightBt.setOnClickListener {
            if(!checarDireita())
                p.moveRight()
        }

        downBt.setOnClickListener {
            if(podeDown())
                p.moveDown()
        }

        rotateBt.setOnClickListener {
            girar()
        }

        pauseBt.setOnClickListener {
            if (pause == 0) {
                running = false
                var i = Intent(this, HomeActivity::class.java)
                var b = Bundle()
                b.putBoolean("cod", true)
                i.putExtras(b)
                startActivityForResult(i, 99)
                pause = 1
            } else {
                running = true
                pause = 0
                gameRun()
            }
        }

    }

    fun gameRun(){
        Thread{
            while(running){
                Thread.sleep(speed[escolhido])
                runOnUiThread{
                    //limpa tela
                    for (i in 0 until LINHA) {
                        for (j in 0 until COLUNA) {
                            if (game.tabuleiro[i][j] == 0){
                                boardView[i][j]!!.setImageResource(R.drawable.branco)
                            }
                        }
                    }

                    //verificar se perdeu
                    perdeu()

                    //mostrar pontos
                    pontAtualText.text = pontos.toString()

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

        val random = (1..7).shuffled().first()

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
        if (p.pA.x+1 >= LINHA ||
            p.pB.x+1 >= LINHA ||
            p.pC.x+1 >= LINHA ||
            p.pD.x+1 >= LINHA){
            return true
        }
        return false
    }

    fun checarDireita():Boolean{
        try {
            if(((game.tabuleiro[p.pA.x][p.pA.y+1] == 0) &&
                (game.tabuleiro[p.pB.x][p.pB.y+1] == 0) &&
                (game.tabuleiro[p.pC.x][p.pC.y+1] == 0) &&
                (game.tabuleiro[p.pD.x][p.pD.y+1] == 0)) ||
                (p.pA.y+1 < COLUNA ||
                 p.pB.y+1 < COLUNA ||
                 p.pC.y+1 < COLUNA ||
                 p.pD.y+1 < COLUNA) ){
                return false
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro direita")
        }
        return true
    }

    fun checarEsquerda():Boolean{
        try {
            if(((game.tabuleiro[p.pA.x][p.pA.y-1] == 0) &&
                (game.tabuleiro[p.pB.x][p.pB.y-1] == 0) &&
                (game.tabuleiro[p.pC.x][p.pC.y-1] == 0) &&
                (game.tabuleiro[p.pD.x][p.pD.y-1] == 0)) ||
                (p.pA.y-1 == 0 ||
                 p.pB.y-1 == 0 ||
                 p.pC.y-1 == 0 ||
                 p.pD.y-1 == 0)){
                return false
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro esquerda")
        }
        return true
    }

    fun podeDown():Boolean{
        if (p.pA.x+1 >= LINHA ||
            p.pB.x+1 >= LINHA ||
            p.pC.x+1 >= LINHA ||
            p.pD.x+1 >= LINHA) {
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
        for (i in 0 until LINHA) {
            if (game.tabuleiro[i].sum() == 12){
                tirar(i)
                pontos +=1
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
        while (p.pA.y < p.giro || p.pA.y > (COLUNA-1) - p.giro){
                    if (p.pA.y<p.giro){
                        p.moveRight()
                    }else{
                        p.moveLeft()
                    }
            }

        //ver se tem espaco em baixo para rodar
        if (p.pA.x < LINHA - p.giro) {
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

            var i = Intent(this,ResultActivity::class.java)
            var pontuacao = pontAtualText.text.toString()
            var b = Bundle()
            b.putString("pontos", pontuacao)
            i.putExtras(b)

            startActivity(i)
            running = false
            finish()
        }

    }

    override fun onPause() {
        super.onPause()
        running = false
    }

    override fun onRestart() {
        super.onRestart()
        running=true
        gameRun()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            99 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        var i = Intent(this, TabuleiroActivity::class.java)
                        startActivity(i)
                    }
                }
            }
        }

    }


}