package com.example.tetris

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_config.*

class ConfigActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        var b = Bundle()

        when(R.id.radioGroup){
            R.id.facilRB ->{
                b.putInt("velocidade", 0)

            }
            R.id.normalRB ->{
                b.putInt("velocidade", 1)
            }
            R.id.dificilRB ->{
                b.putInt("velocidade", 2)
            }
        }

        saveBt.setOnClickListener {
            var i = Intent(this,ConfigActivity::class.java)
            i.putExtras(b)
            setResult(Activity.RESULT_OK, i)
            finish()
        }

    }
}
