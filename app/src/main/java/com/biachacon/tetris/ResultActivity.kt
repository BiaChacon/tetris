package com.biachacon.tetris

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    //var params = intent.extras

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //pegar pontos
        var params = intent.extras
        var pontos = params?.getString("pontos")

        //pegar prefs
        val settings = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        var editor = settings.edit()

        //mostrar pontos da partida
        pontuacaoText.text = pontos.toString()

        //pegar record
        var record = settings.getInt("record", 0)
        pontoRecordText.text = record.toString()

        //verificar se o record foi superado
        if(pontos.toString().toInt()>record){
            newRecordText.visibility = View.VISIBLE
            editor.putInt("record", pontos.toString().toInt())
            editor.commit()
        }

        sairBt.setOnClickListener {
            finish()
        }

        newJogoBt.setOnClickListener {
            var i = Intent(this, TabuleiroActivity::class.java)
            startActivity(i)
        }

    }
}
