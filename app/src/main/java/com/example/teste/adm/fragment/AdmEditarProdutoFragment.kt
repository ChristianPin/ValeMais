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
import com.example.teste.classe.categoriaAdm
import com.example.teste.classe.idProdutoAdm
import com.example.teste.model.Produto
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_adm_editar_produto.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AdmEditarProdutoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_adm_editar_produto, container, false)

        infoProduto()

        val btnCancelar = view.findViewById<View>(R.id.btnCancelar) as Button
        btnCancelar.setOnClickListener {
            val fragment = ListaProdutoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnUpload = view.findViewById<View>(R.id.btnUpload) as Button
        btnUpload.setOnClickListener {
            Log.d("EdicaoProduto", "Clicou botão upload")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val btnSalvar = view.findViewById<View>(R.id.btnSalvar) as Button
        btnSalvar.setOnClickListener {
            editar()

            if(categoriaAdm.equals("Queer box")){
                val fragment = ListaQueerBoxFragment()
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.containerAdm, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }else{
                val fragment = ListaProdutoFragment()
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.containerAdm, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        return view
    }

    lateinit var prodId: String
    lateinit var categoria: String
    lateinit var imgAntiga: String
    private fun infoProduto(){
        lateinit var ref: DatabaseReference
        if(categoriaAdm.equals("Queer box")){
            Log.d("AdmEditarProduto", "queer")
            ref = FirebaseDatabase.getInstance().getReference("queer box").child(idProdutoAdm)
        }else{
            Log.d("AdmEditarProduto", "outros")
            ref = FirebaseDatabase.getInstance().getReference("produtos").child(idProdutoAdm)
        }
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val prod = dataSnapshot.getValue(Produto::class.java)
                addNome.setText(prod?.nome)
                addNome.setSelection(addNome.text.length)

                addDesc.setText(prod?.desc)
                addDesc.setSelection(addDesc.text.length)

                addTam.setText(prod?.tam)
                addTam.setSelection(addTam.text.length)

                addQtde.setText(prod?.qtde)
                addQtde.setSelection(addQtde.text.length)

                addValor.setText(prod?.valor)
                addValor.setSelection(addValor.text.length)

                Picasso.get().load(prod?.img).into(img)

                prodId = prod?.prodId.toString()
                categoria = prod?.categoria.toString()
                imgAntiga = prod?.img.toString()
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

    private fun editar() {
        val nome = addNome.text.toString().trim()
        val desc = addDesc.text.toString().trim()
        val tam = addTam.text.toString().trim()
        val qtde = addQtde.text.toString().trim()
        val valor = addValor.text.toString().trim()

        var imgNova : String

        if (nome.isEmpty() || desc.isEmpty() || tam.isEmpty() || valor.isEmpty() || qtde.isEmpty()){
            Toast.makeText(activity, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        }else {
            if(uriFotoSelecionada == null){

                lateinit var ref: DatabaseReference
                if(categoriaAdm.equals("Queer box")){
                    Log.d("AdmEditarProduto", "queer")
                    ref = FirebaseDatabase.getInstance().getReference("queer box").child(prodId)
                }else{
                    Log.d("AdmEditarProduto", "outros")
                    ref = FirebaseDatabase.getInstance().getReference("produtos").child(prodId)
                }

                imgNova = imgAntiga

                val produto = Produto(
                    prodId,
                    nome,
                    desc,
                    tam,
                    qtde,
                    valor,
                    categoria,
                    imgNova
                )
                ref.setValue(produto)
            }else{
                lateinit var ref: DatabaseReference
                if(categoriaAdm.equals("Queer box")){
                    Log.d("AdmEditarProduto", "queer")
                    ref = FirebaseDatabase.getInstance().getReference("queer box").child(prodId)
                }else{
                    Log.d("AdmEditarProduto", "outros")
                    ref = FirebaseDatabase.getInstance().getReference("produtos").child(prodId)
                }

                imgNova = url

                val produto = Produto(
                    prodId,
                    nome,
                    desc,
                    tam,
                    qtde,
                    valor,
                    categoria,
                    imgNova
                )
                ref.setValue(produto)
            }
            Log.d("EdicaoProduto", "Produto atualizado com sucesso!")
            Toast.makeText(
                activity,
                "Produto atualizado com sucesso!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    var uriFotoSelecionada : Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Log.d("EdicaoProduto", "Foto selecionada")

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
        val ref = FirebaseStorage.getInstance().getReference("/produtos/$nomeImg")

        ref.putFile(uriFotoSelecionada!!)
            .addOnSuccessListener {
                Log.d("EdicaoProduto", "Imagem salva com sucesso: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("EdicaoProduto", "Location: $it")
                    url = it.toString()
                }
            }
    }
}
