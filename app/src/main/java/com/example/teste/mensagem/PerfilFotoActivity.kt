package com.example.teste.mensagem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teste.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_perfil_foto.*

class PerfilFotoActivity : AppCompatActivity() {


    companion object{
        val NOME_PERFIL = "NOME_PERFIL"
        val FOTO_PERFIL = "FOTO_PERFIL"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_foto)


        val nomeUser = intent.getStringExtra(ChatLogActivity.NOME_PERFIL)
        val fotoUser = intent.getStringExtra(ChatLogActivity.FOTO_PERFIL)

        nome_chat.text = nomeUser
        Picasso.get().load(fotoUser).into(foto_chat)



    }
}
