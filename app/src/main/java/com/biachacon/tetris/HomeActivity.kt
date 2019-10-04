package com.biachacon.tetris

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        newGameBt.setOnClickListener(this)
        confBt.setOnClickListener(this)
        continuaBt.setOnClickListener(this)

        var params = intent.extras
        var continuar = params?.getBoolean("cod")

        when (continuar) {
            true -> {
                continuaBt.visibility = View.VISIBLE
            }
        }


    }

    override fun onClick(view: View) {
        val id = view.id
        when(id){
            newGameBt.id->{
                var i = Intent(this, TabuleiroActivity::class.java)
                startActivity(i)
            }
            confBt.id->{
                var i = Intent(this, ConfigActivity::class.java)
                startActivityForResult(i,99)
            }
            continuaBt.id->{
                var i = Intent()
                setResult(Activity.RESULT_OK, i)
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var settings = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        var editor = settings.edit()
        val params = data?.extras

        when(resultCode){
            Activity.RESULT_OK->{
                editor.putInt("dificuldade", params!!.getInt("dificuldade"))
                editor.commit()
            }
        }
    }

}
