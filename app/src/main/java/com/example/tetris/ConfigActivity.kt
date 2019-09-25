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



        saveBt.setOnClickListener {
           if (normalRB.isChecked)
               b.putInt("dificuldade", 0)
           if(facilRB.isChecked)
               b.putInt("dificuldade", 1)
           if (dificilRB.isChecked)
               b.putInt("dificuldade", 2)


            var i = Intent(this,HomeActivity::class.java)
            i.putExtras(b)
            setResult(Activity.RESULT_OK, i)
            finish()
        }

    }
}
