package com.example.teste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.teste.adm.activity.AdmHomeActivity
import com.example.teste.cliente.activity.HomeActivity
import com.example.teste.mensagem.UltimasMensagensActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnResgateSenha.setOnClickListener {
            val i = Intent(this, ResgatarSenhaActivity::class.java)
            startActivity(i)
        }

        btnEntrar.setOnClickListener {
            login()
        }

        btnCadastrar.setOnClickListener {
            val i = Intent(this, CadUserActivity::class.java)
            startActivity(i)
        }
    }

    private fun login() {
        if (email.text.toString().isEmpty()) {
            email.error = "Digite o seu email"
            email.requestFocus()
            return
        }

        if (senha.text.toString().isEmpty()) {
            senha.error = "Digite a sua senha"
            senha.requestFocus()
            return
        }

        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email.text.toString(), senha.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (senha.text.toString().equals("adm12345")) {
                        val i = Intent(this, AdmHomeActivity::class.java)
                        startActivity(i)
                    } else {
                        val i = Intent(this, HomeActivity::class.java)
                        startActivity(i)
                    }
                } else {
                    Toast.makeText(
                        baseContext,
                        "Não foi possível realizar o login. Email ou senha incorreto(s).",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
