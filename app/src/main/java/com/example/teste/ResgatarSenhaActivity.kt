package com.example.teste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResgatarSenhaActivity : AppCompatActivity() {

    private val TAG = "EsquecerSenhaActivity"

    lateinit var addEmail: EditText
    lateinit var btnRec: Button

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resgatar_senha)

        initialize()
    }

    private fun initialize(){
        addEmail = findViewById(R.id.txt_email) as EditText
        btnRec = findViewById<Button>(R.id.btn_rec) as Button

        mAuth = FirebaseAuth.getInstance()

        btnRec!!.setOnClickListener{sendPasswordEmail()}
    }

    private fun sendPasswordEmail(){
        val email = addEmail.text.toString()

        if(!TextUtils.isEmpty(email)){
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        val message = "Email enviado com sucesso!"
                        Log.d(TAG, message)
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                        updateUI()
                    } else {
                        Log.e(TAG, task.exception!!.message)
                        Toast.makeText(this, "Nenhum usuário cadastrado com este email!", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, "Email Inválido!", Toast.LENGTH_LONG).show()

        }

    }

    private fun updateUI(){
        val intent = Intent(this@ResgatarSenhaActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
