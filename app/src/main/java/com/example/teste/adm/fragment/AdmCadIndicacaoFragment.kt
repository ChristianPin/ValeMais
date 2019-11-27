package com.example.teste.adm.fragment


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.teste.R
import com.example.teste.model.Indicacao
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.fragment_adm_cad_indicacao.*
import kotlinx.android.synthetic.main.fragment_adm_cad_indicacao.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AdmCadIndicacaoFragment : Fragment() {

    lateinit var addNome: EditText
    lateinit var addDesc: EditText
    lateinit var img: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_adm_cad_indicacao, container, false)

        addNome = view.findViewById(R.id.addNome)
        addDesc = view.findViewById(R.id.addDesc)

        val btnUpload = view.findViewById<View>(R.id.btnUpload) as Button
        btnUpload.setOnClickListener {
            Log.d("AdmCadIndicacaoFragment", "Clicou botão upload")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val btnCancelar = view.findViewById<View>(R.id.btnCancelar) as Button
        btnCancelar.setOnClickListener {
            val fragment = MenuAdmIndicacaoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnCadastrar = view.findViewById<View>(R.id.btnCadastrar) as Button
        btnCadastrar.setOnClickListener {
            cadastrarIndicacao()

            val fragment = ListaIndicacaoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }

        return view
    }

    var uriFotoSelecionada: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("AdmCadIndicacaoFragment", "Foto selecionada")

            uriFotoSelecionada = data.data

            Log.d("AdmCadIndicacaoFragment", "URI: $uriFotoSelecionada")

            btnUpload.alpha = 0f

            uploadImagem()
        }
    }

    private fun uploadImagem() {
        if (uriFotoSelecionada == null) return
        val nomeImg = UUID.randomUUID().toString()
        Log.d("AdmCadIndicacaoFragment", "Nome img: $nomeImg")
        val ref = FirebaseStorage.getInstance().getReference("/indicacoes/$nomeImg")

        ref.putFile(uriFotoSelecionada!!).addOnSuccessListener {
            Log.d("AdmCadIndicacaoFragment", "Imagem salva com sucesso: ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                Log.d("AdmCadIndicacaoFragment", "Location: $it")
                img = it.toString()
            }
        }
    }

    lateinit var indicId: String

    private fun cadastrarIndicacao() {
        val nome = addNome.text.toString().trim()
        val desc = addDesc.text.toString().trim()

        if (nome.isEmpty() || desc.isEmpty() || uriFotoSelecionada == null) {
            Toast.makeText(activity, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        } else {
            val ref = FirebaseDatabase.getInstance().getReference("indicacoes")
            indicId = ref.push().key.toString()
            val indicacao = Indicacao(
                indicId,
                nome,
                desc,
                img
            )
            ref.child(indicId).setValue(indicacao).addOnCompleteListener {
                Log.d("AdmCadIndicacaoFragment", "Indicação cadastrada com sucesso!")
                Toast.makeText(
                    activity,
                    "Indicação cadastrada com sucesso!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
