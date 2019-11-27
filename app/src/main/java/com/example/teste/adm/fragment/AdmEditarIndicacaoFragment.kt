package com.example.teste.adm.fragment


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
import com.example.teste.classe.idIndicacaoAdm
import com.example.teste.model.Indicacao
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_adm_editar_indicacao.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AdmEditarIndicacaoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_adm_editar_indicacao, container, false)

        infoIndicacao()

        val btnCancelar = view.findViewById<View>(R.id.btnCancelar) as Button
        btnCancelar.setOnClickListener {
            val fragment = ListaIndicacaoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnUpload = view.findViewById<View>(R.id.btnUpload) as Button
        btnUpload.setOnClickListener {
            Log.d("EdicaoIndic", "Clicou botão upload")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val btnSalvar = view.findViewById<View>(R.id.btnSalvar) as Button
        btnSalvar.setOnClickListener {
            editar()

            val fragment = ListaIndicacaoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }

    lateinit var indicId: String
    lateinit var imgAntiga: String
    private fun infoIndicacao(){
        var ref = FirebaseDatabase.getInstance().getReference("indicacoes").child(idIndicacaoAdm)
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val indic = dataSnapshot.getValue(Indicacao::class.java)
                addNome.setText(indic?.nome)
                addNome.setSelection(addNome.text.length)

                addDesc.setText(indic?.desc)
                addDesc.setSelection(addDesc.text.length)

                Picasso.get().load(indic?.img).into(img)

                indicId = indic?.indicId.toString()
                imgAntiga = indic?.img.toString()
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

    private fun editar() {
        val nome = addNome.text.toString().trim()
        val desc = addDesc.text.toString().trim()

        var imgNova : String

        if (nome.isEmpty() || desc.isEmpty()){
            Toast.makeText(activity, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        }else {
            if(uriFotoSelecionada == null){

                var ref = FirebaseDatabase.getInstance().getReference("indicacoes").child(indicId)

                imgNova = imgAntiga

                val indic = Indicacao(
                    indicId,
                    nome,
                    desc,
                    imgNova
                )
                ref.setValue(indic)
            }else{
                var ref = FirebaseDatabase.getInstance().getReference("indicacoes").child(indicId)

                imgNova = url

                val indic = Indicacao(
                    indicId,
                    nome,
                    desc,
                    imgNova
                )
                ref.setValue(indic)
            }
            Log.d("EdicaoIndic", "Indicação atualizada com sucesso!")
            Toast.makeText(
                activity,
                "Indicação atualizada com sucesso!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    var uriFotoSelecionada : Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Log.d("EdicaoIndic", "Foto selecionada")

            uriFotoSelecionada = data.data

            val imgNova = BitmapFactory.decodeFile(uriFotoSelecionada?.getPath())
            img.setImageBitmap(imgNova)

            uploadImagem()
        }
    }

    lateinit var url : String

    private fun uploadImagem() {
        if(uriFotoSelecionada == null) return
        val nomeImg = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/indicacoes/$nomeImg")

        ref.putFile(uriFotoSelecionada!!)
            .addOnSuccessListener {
                Log.d("EdicaoIndic", "Imagem salva com sucesso: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("EdicaoIndic", "Location: $it")
                    url = it.toString()
                }
            }
    }
}