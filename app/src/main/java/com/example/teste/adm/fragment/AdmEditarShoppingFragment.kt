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
import com.example.teste.classe.idShoppingAdm
import com.example.teste.model.Shopping
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_adm_editar_shopping.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AdmEditarShoppingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_adm_editar_shopping, container, false)

        infoShopping()

        val btnCancelar = view.findViewById<View>(R.id.btnCancelar) as Button
        btnCancelar.setOnClickListener {
            val fragment = ListaShoppingFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnUpload = view.findViewById<View>(R.id.btnUpload) as Button
        btnUpload.setOnClickListener {
            Log.d("EdicaoShopping", "Clicou botão upload")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val btnSalvar = view.findViewById<View>(R.id.btnSalvar) as Button
        btnSalvar.setOnClickListener {
            editar()

            val fragment = ListaShoppingFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }

    private fun editar(){
        val nome = addNome.text.toString().trim()
        val end = addEnd.text.toString().trim()
        val horaInicio = addHoraInicio.text.toString().trim()
        val horaFim = addHoraFim.text.toString().trim()
        val dia = addDia.text.toString().trim()

        var imgNova: String

        if (nome.isEmpty() || end.isEmpty() || horaInicio.isEmpty() || horaFim.isEmpty() || dia.isEmpty()) {
            Toast.makeText(
                activity,
                "Preencha todos os campos!",
                Toast.LENGTH_LONG
            ).show()
        } else {
            if (uriFotoSelecionada == null) {
                imgNova = imgAntiga
                val ref =
                    FirebaseDatabase.getInstance().getReference("shoppings").child(shoppId)
                val shopping =
                    Shopping(
                        shoppId,
                        nome,
                        end,
                        horaInicio,
                        horaFim,
                        dia,
                        imgNova
                    )
                ref.setValue(shopping)
            } else {
                imgNova = url
                val ref =
                    FirebaseDatabase.getInstance().getReference("shoppings").child(shoppId)
                val shopping =
                    Shopping(
                        shoppId,
                        nome,
                        end,
                        horaInicio,
                        horaFim,
                        dia,
                        imgNova
                    )
                ref.setValue(shopping)
            }
            Log.d("EdicaoShopping", "Shopping atualizado com sucesso!")
            Toast.makeText(
                activity,
                "Shopping atualizado com sucesso!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    lateinit var shoppId: String
    lateinit var imgAntiga: String
    private fun infoShopping(){
        var ref = FirebaseDatabase.getInstance().getReference("shoppings").child(idShoppingAdm)
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val shopp = dataSnapshot.getValue(Shopping::class.java)
                addNome.setText(shopp?.nome)
                addNome.setSelection(addNome.text.length)

                addEnd.setText(shopp?.endereco)
                addEnd.setSelection(addEnd.text.length)

                addDia.setText(shopp?.dia)
                addDia.setSelection(addDia.text.length)

                addHoraInicio.setText(shopp?.horaInicio)
                addHoraInicio.setSelection(addHoraInicio.text.length)

                addHoraFim.setText(shopp?.horaFim)
                addHoraFim.setSelection(addHoraFim.text.length)

                Picasso.get().load(shopp?.img).into(img)

                shoppId = shopp?.shoppId.toString()
                imgAntiga = shopp?.img.toString()
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

    var uriFotoSelecionada : Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Log.d("EdicaoShopping", "Foto selecionada")

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
        val ref = FirebaseStorage.getInstance().getReference("/shoppings/$nomeImg")

        ref.putFile(uriFotoSelecionada!!)
            .addOnSuccessListener {
                Log.d("EdicaoShopping", "Imagem salva com sucesso: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("EdicaoShopping", "Location: $it")
                    url = it.toString()
                }
            }
    }
}
