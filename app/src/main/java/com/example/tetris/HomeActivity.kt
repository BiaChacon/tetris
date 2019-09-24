package com.example.tetris

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {


    val PREFS = "prefs_file"
    val CODE = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        var settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        var edit = settings.edit()

        newGameBt.setOnClickListener {
            var i = Intent(this,TabuleiroActivity::class.java)
            var b = Bundle()
            b.putInt("velocidade", settings.getInt("velocidade", 1))
            i.putExtras(b)
            startActivity(i)
            finish()
        }

        confBt.setOnClickListener {
            var i = Intent(this,ConfigActivity::class.java)
            startActivityForResult(i, CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var p = data?.extras
        var settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        var edit = settings.edit()
        when(resultCode){
            Activity.RESULT_OK ->{
                edit.putInt("velocidade", p!!.getInt("velocidade"))
                edit.commit()
            }
        }

    }

}
