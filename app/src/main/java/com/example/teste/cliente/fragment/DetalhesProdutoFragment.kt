package com.example.teste.cliente.fragment


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog

import com.example.teste.R
import com.example.teste.classe.Carrinho
import com.example.teste.classe.Produto
import com.example.teste.classe.cpfUserLogado
import com.example.teste.classe.idProdSelecionado
import com.example.teste.model.ProdutoModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detalhes_produto.*

/**
 * A simple [Fragment] subclass.
 */
class DetalhesProdutoFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    lateinit var qtde: String
    lateinit var textQtde : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detalhes_produto, container, false)

        textQtde = view.findViewById(R.id.textQtde)

        infoProduto()

        val btnMais = view.findViewById<View>(R.id.btnMais) as Button
        btnMais.setOnClickListener {
            Log.i("DetalhesProduto", "NUM TEXTVIEW2: ${textQtde.text}")
            if(textQtde.text.toString().toInt() < qtde.toInt()) {
                var textQtdeAtual = textQtde.text.toString()
                var textQtdeAdd = textQtdeAtual.toInt() + 1
                textQtde.setText(textQtdeAdd.toString())
                btnMenos.isEnabled = true
            }else{
                btnMais.isEnabled = false
            }
        }

        val btnMenos = view.findViewById<View>(R.id.btnMenos) as Button
        btnMenos.setOnClickListener {
            if(textQtde.text.toString().toInt() > 1) {
                var textQtdeAtual = textQtde.text.toString()
                var textQtdeAdd = textQtdeAtual.toInt() - 1
                textQtde.setText(textQtdeAdd.toString())
                btnMais.isEnabled = true
            }else{
                btnMenos.isEnabled = false
            }
        }

        val btnCar = view.findViewById<View>(R.id.btnCar) as Button
        btnCar.setOnClickListener {
            addCar()
        }

        return view
    }

    private fun infoProduto() {
        var ref = FirebaseDatabase.getInstance().getReference("produtos").child(idProdSelecionado)
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val produto = dataSnapshot.getValue(ProdutoModel::class.java)
                nome.text = produto?.nome
                desc.text = produto?.desc
                tam.text = produto?.tam
                Picasso.get().load(produto?.img).into(img)
                textQtde.text = "1"
                qtde = produto?.qtde.toString()
                Log.i("DetalhesProduto", "QTDE: $qtde")
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

    private fun addCar(){
        auth = FirebaseAuth.getInstance()
        var idUserAtual = auth.currentUser?.uid
        Log.i("DetalhesProduto", "ID USER: $idUserAtual")
        var ref = FirebaseDatabase.getInstance().getReference("produtos").child(idProdSelecionado)
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val produto = dataSnapshot.getValue(ProdutoModel::class.java)
                var prodId = produto?.prodId
                Log.i("DetalhesProduto", "PRODUTO: $produto")
                var qtdeEstoque = qtde.toInt() - textQtde.text.toString().toInt()
                var qtdeCliente = textQtde.text.toString()
                val ref1 = FirebaseDatabase.getInstance().getReference("carrinho")
                val carrinho = Carrinho(cpfUserLogado, produto?.prodId.toString(), produto?.nome.toString(), produto?.desc.toString(), produto?.tam.toString(), qtdeCliente, produto?.valor.toString(), produto?.categoria.toString(), produto?.img.toString())
                ref1.child(cpfUserLogado).child(prodId).setValue(carrinho)
                val ref2 = FirebaseDatabase.getInstance().getReference("produtos")
                val produtoEstoque = Produto(produto?.prodId.toString(), produto?.nome.toString(), produto?.desc.toString(), produto?.tam.toString(), qtdeEstoque.toString(), produto?.valor.toString(), produto?.categoria.toString(), produto?.img.toString())
                ref2.child(prodId).setValue(produtoEstoque)
            }
        }
        ref.addListenerForSingleValueEvent(info)

        val mAlertDialog = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
        mAlertDialog.setTitle("Produto Adicionado")
        mAlertDialog.setCanceledOnTouchOutside(true)
        mAlertDialog.hideConfirmButton()
        mAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"))
        mAlertDialog.getProgressHelper().setProgress(maxOf(5f,1000f))
        Log.i("DetalhesProduto", "PAROU: ${mAlertDialog.getProgressHelper().getProgress()}")
        mAlertDialog.show()


        Log.i("DetalhesProduto", "PAROU: ${mAlertDialog.getProgressHelper().getProgress()}")

        if(mAlertDialog.getProgressHelper().getProgress() == 1000f){
            Log.i("DetalhesProduto", "PRODUTO parou: pena")

            //mAlertDialog.dismissWithAnimation()
            val fragment = DestaquesFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
            //mAlertDialog.dismissWithAnimation()

        }



    }
}
