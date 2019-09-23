package com.example.tetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        newGameBt.setOnClickListener {
            var i = Intent(this,TabuleiroActivity::class.java)
            startActivity(i)
            finish()
        }

        confBt.setOnClickListener {
            var i = Intent(this,ConfigActivity::class.java)
            startActivity(i)
        }

    }

}
