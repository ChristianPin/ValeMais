package com.example.teste.cliente.activity
import android.content.Intent
import com.example.teste.R

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_comunidade_vale.*

class ComunidadeValeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comunidade_vale)

        btnSerie.setOnClickListener {
            val i = Intent(this, SerieActivity::class.java)
            startActivity(i)
        }
    }
}
