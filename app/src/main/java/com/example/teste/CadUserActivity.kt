package com.example.teste

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.teste.adm.activity.AdmHomeActivity
import com.example.teste.classe.User
import com.example.teste.cliente.activity.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_cad_user.*
import java.util.*

class CadUserActivity : AppCompatActivity() {

    lateinit var addNome: EditText
    lateinit var addCpf: EditText
    lateinit var addTel: EditText
    lateinit var addNasc: EditText
    lateinit var addEmail: EditText
    lateinit var addSenha: EditText
    lateinit var btnCadastar: Button
    lateinit var img: String

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cad_user)

        addNome = findViewById(R.id.addNome)
        addCpf = findViewById(R.id.addCpf)
        addTel = findViewById(R.id.addTel)
        addNasc = findViewById(R.id.addNasc)
        addEmail = findViewById(R.id.addEmail)
        addSenha = findViewById(R.id.addSenha)
        btnCadastar = findViewById(R.id.btnCadastrar)

        btnUpload.setOnClickListener {
            Log.d("UploadActivity", "Clicou botão upload")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        btnVoltar.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        btnCadastar.setOnClickListener {
            cadastrarUser()
        }
    }

    var uriFotoSelecionada: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("UploadActivity", "Foto selecionada")

            uriFotoSelecionada = data.data

            Log.d("UploadActivity", "URI: $uriFotoSelecionada")

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uriFotoSelecionada)

            circleImageView.setImageBitmap(bitmap)
            btnUpload.alpha = 0f

            uploadImagem()
            //val bitmapDrawable = BitmapDrawable(bitmap)
            //btnUpload.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun uploadImagem() {
        if (uriFotoSelecionada == null) return
        val nomeImg = UUID.randomUUID().toString()
        Log.d("UploadActivity", "Nome img: $nomeImg")
        val ref = FirebaseStorage.getInstance().getReference("/users/$nomeImg")

        ref.putFile(uriFotoSelecionada!!).addOnSuccessListener {
            Log.d("UploadActivity", "Imagem salva com sucesso: ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                Log.d("UploadActivity", "Location: $it")
                img = it.toString()
            }
        }
    }

    private fun cadastrarUser() {
        val nome = addNome.text.toString().trim()
        val cpf = addCpf.text.toString().trim()
        val tel = addTel.text.toString().trim()
        val nasc = addNasc.text.toString().trim()
        val email = addEmail.text.toString().trim()
        val senha = addSenha.text.toString().trim()


        if (nome.isEmpty() || cpf.isEmpty() || tel.isEmpty() || nasc.isEmpty() || email.isEmpty() || senha.isEmpty() || uriFotoSelecionada == null) {
            Toast.makeText(applicationContext, "Preencha todos os campos!", Toast.LENGTH_LONG)
                .show()
        } else {

            val ref = FirebaseDatabase.getInstance().getReference("users")

            auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        var userId = auth.currentUser?.uid.toString()

                        Log.d("UploadActivity", "ID USER: $userId")

                        val user = User(
                            userId,
                            nome,
                            cpf,
                            tel,
                            nasc,
                            email,
                            senha,
                            img
                        )
                        ref.child(cpf).setValue(user).addOnCompleteListener {
                            Toast.makeText(
                                applicationContext,
                                "Usuário cadastrado com sucesso!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        /*Toast.makeText(
                            baseContext, "Authentication concluída.",
                            Toast.LENGTH_SHORT
                        ).show()*/

                        if (senha.equals("adm12345")) {
                            val i = Intent(this, AdmHomeActivity::class.java)
                            startActivity(i)
                        } else {
                            val i = Intent(this, HomeActivity::class.java)
                            startActivity(i)
                        }
                    } else {
                        /*Toast.makeText(
                            baseContext, "Authentication falhou.",
                            Toast.LENGTH_SHORT
                        ).show()*/
                        Log.d("UploadActivity", "Authentication falhou.")
                    }

                }
        }
    }
}