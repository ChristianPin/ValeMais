package com.example.teste.cliente.fragment


import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import com.example.teste.R
import com.example.teste.classe.cpfUserLogado
import com.example.teste.model.User
import com.example.teste.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_editar_conta.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class EditarContaFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_editar_conta, container, false)

        val btnCancelar = view.findViewById<View>(R.id.btnCancelar) as Button
        btnCancelar.setOnClickListener {
            val fragment = AjustesFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnUpload = view.findViewById<View>(R.id.btnUpload) as Button
        btnUpload.setOnClickListener {
            Log.d("EdicaoUser", "Clicou botão upload")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val btnSalvar = view.findViewById<View>(R.id.btnSalvar) as Button
        btnSalvar.setOnClickListener {
            editar()
            val fragment = AjustesFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        infoUser()

        return view
    }

    lateinit var emailAntigo: String
    lateinit var imgAntiga: String
    lateinit var cpf: String
    lateinit var idAntigo: String
    lateinit var nasc: String
    private fun infoUser() {
        var ref = FirebaseDatabase.getInstance().getReference("users").child(cpfUserLogado)
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(UserModel::class.java)
                addNome.setText(user?.nome)
                addNome.setSelection(addNome.text.length)
                addTel.setText(user?.tel)
                addTel.setSelection(addTel.text.length)
                addEmail.setText(user?.email)
                addEmail.setSelection(addEmail.text.length)
                addSenha.setText(user?.senha)
                addSenha.setSelection(addSenha.text.length)
                Picasso.get().load(user?.img).into(img)

                emailAntigo = user?.email.toString()
                imgAntiga = user?.img.toString()
                cpf = user?.cpf.toString()
                idAntigo = user?.userId.toString()
                nasc = user?.nasc.toString()
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

    var uriFotoSelecionada: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("EdicaoUser", "Foto selecionada")

            uriFotoSelecionada = data.data

            val imgNova = BitmapFactory.decodeFile(uriFotoSelecionada?.getPath())
            img.setImageBitmap(imgNova)

            uploadImagem()
        }
    }

    lateinit var url: String
    private fun uploadImagem() {
        if (uriFotoSelecionada == null) return
        val nomeImg = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/users/$nomeImg")

        ref.putFile(uriFotoSelecionada!!)
            .addOnSuccessListener {
                Log.d("EdicaoUser", "Imagem salva com sucesso: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("EdicaoUser", "Location: $it")
                    url = it.toString()
                }
            }
    }

    private fun editar() {
        addNome.setSelection(addNome.text.length)
        addTel.setSelection(addTel.text.length)
        addEmail.setSelection(addEmail.text.length)
        addSenha.setSelection(addSenha.text.length)

        val nome = addNome.text.toString().trim()
        val tel = addTel.text.toString().trim()
        val email = addEmail.text.toString().trim()
        val senha = addSenha.text.toString().trim()

        if (nome.isEmpty() || tel.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(activity, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        } else {
            if (emailAntigo.equals(email)) {
                if (uriFotoSelecionada == null) {
                    val ref =
                        FirebaseDatabase.getInstance().getReference("users").child(cpfUserLogado)
                    val user = User(idAntigo, nome, cpf, tel, nasc, email, senha, imgAntiga)
                    ref.setValue(user)
                } else {
                    val ref =
                        FirebaseDatabase.getInstance().getReference("users").child(cpfUserLogado)
                    val user = User(idAntigo, nome, cpf, tel, nasc, email, senha, url)
                    ref.setValue(user)
                }
            } else {
                val userAtual = FirebaseAuth.getInstance().currentUser

                userAtual?.delete()
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("ExclusaoUser", "User deletado.")
                        }
                    }
                if (uriFotoSelecionada == null) {
                    val ref =
                        FirebaseDatabase.getInstance().getReference("users").child(cpfUserLogado)
                    auth = FirebaseAuth.getInstance()
                    auth.createUserWithEmailAndPassword(email, senha).addOnSuccessListener {
                        var idNovo = auth.currentUser?.uid.toString()
                        val user = User(idNovo, nome, cpf, tel, nasc, email, senha, imgAntiga)
                        ref.setValue(user)
                    }
                }else{
                    val ref =
                        FirebaseDatabase.getInstance().getReference("users").child(cpfUserLogado)
                    auth = FirebaseAuth.getInstance()
                    auth.createUserWithEmailAndPassword(email, senha).addOnSuccessListener {
                        var idNovo = auth.currentUser?.uid.toString()
                        val user = User(idNovo, nome, cpf, tel, nasc, email, senha, url)
                        ref.setValue(user)
                    }
                }
            }
            Toast.makeText(activity, "Alterações salvas com sucesso!", Toast.LENGTH_LONG).show()
        }
    }
}
