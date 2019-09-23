package com.example.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    var params = intent.extras

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        pontuacaoText.text = params!!.getInt("pontuacao").toString()

        if(params!!.getInt("pontuacao")<1)
           newRecordText.invalidate()

        sairBt.setOnClickListener {
            finish()
        }

    }
}
