package com.example.teste.adm.fragment


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.teste.LoginActivity

import com.example.teste.R
import com.example.teste.classe.User
import com.example.teste.classe.cpfUserLogado
import com.example.teste.mensagem.UltimasMensagensActivity
import com.example.teste.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_adm_ajustes.*

/**
 * A simple [Fragment] subclass.
 */
class AdmAjustesFragment : Fragment() {

    lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_adm_ajustes, container, false)

        ref = FirebaseDatabase.getInstance().getReference("users")

        val btnEditarPerfilAdm = view.findViewById<View>(R.id.btnEditarPerfilAdm) as ImageView

        btnEditarPerfilAdm.setOnClickListener {
            val fragment = AdmEditarContaFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnExcluir = view.findViewById<View>(R.id.btnExcluir) as CardView

        btnExcluir.setOnClickListener {
            excluir()
        }

        val btnSair = view.findViewById<View>(R.id.btnSair) as CardView

        btnSair.setOnClickListener {
            sair()
        }

        val btnChat = view.findViewById<View>(R.id.btnChat) as CardView

        btnChat.setOnClickListener {
            startActivity(Intent(context, UltimasMensagensActivity::class.java))
        }

        infoUser()

        return view
    }

    private fun infoUser() {
        var ref = FirebaseDatabase.getInstance().getReference("users").child(cpfUserLogado)
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(UserModel::class.java)
                nome_usuario.text = user?.nome
                email_usuario.text = user?.email
                Picasso.get().load(user?.img).into(foto_usuario)
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

    private fun sair() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Sair")
        builder.setMessage("Deseja mesmo sair do aplicativo?")

        builder.setPositiveButton("Sim") { dialog, which ->
            auth = FirebaseAuth.getInstance()
            var idUserAtual = auth.currentUser?.uid
            Log.d("AdmAjustesFragment", "id user atual: $idUserAtual")
            auth.signOut()
            val i = Intent(activity, LoginActivity::class.java)
            startActivity(i)
        }

        builder.setNegativeButton("Não") { dialog, which ->
            Toast.makeText(
                activity,
                "Ação cancelada!",
                Toast.LENGTH_SHORT
            ).show()
        }

        builder.show()
    }

    private fun excluir() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Excluir conta")
        builder.setMessage("Deseja mesmo excluir a sua conta?")

        builder.setPositiveButton("Sim") { dialog, which ->
            Toast.makeText(
                activity,
                "Sua conta foi excluída!",
                Toast.LENGTH_SHORT
            ).show()
            auth = FirebaseAuth.getInstance()
            var user = auth.currentUser
            ref.child(cpfUserLogado).removeValue()
            user?.delete()
            val i = Intent(activity, LoginActivity::class.java)
            startActivity(i)
        }

        builder.setNegativeButton("Não") { dialog, which ->
            Toast.makeText(
                activity,
                "Exclusão cancelada!",
                Toast.LENGTH_SHORT
            ).show()
        }

        builder.show()
    }
}
