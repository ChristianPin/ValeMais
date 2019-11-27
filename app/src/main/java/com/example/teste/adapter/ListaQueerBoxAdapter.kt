package com.example.teste.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.adm.activity.AdmHomeActivity
import com.example.teste.adm.fragment.AdmEditarProdutoFragment
import com.example.teste.classe.categoriaAdm
import com.example.teste.classe.idProdutoAdm
import com.example.teste.model.Produto
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ListaQueerBoxAdapter(
    private val context: Context,
    private val listQueerBox: ArrayList<Produto>
) : RecyclerView.Adapter<ListaQueerBoxAdapter.MyViewHolder>() {

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var (prodId, nome, desc, tam, qtde, valor, categoria, img) = listQueerBox[i]

        Picasso.get().load(listQueerBox[i].img).into(myViewHolder.img)
        myViewHolder.nome.text = nome

        myViewHolder.btnExcluir.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("queer box")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmação de exclusão")
            builder.setMessage("Deseja realmente excluir: $nome?")
            Log.d("ExclusaoProdutoActivity", "ID PRODUTO: $prodId")

            builder.setPositiveButton("Sim") { dialog, which ->
                Toast.makeText(context, "Produto excluído!", Toast.LENGTH_SHORT).show()
                ref.child(prodId).removeValue()
            }

            builder.setNegativeButton("Não") { dialog, which ->
                Toast.makeText(context, "Exclusão cancelada!", Toast.LENGTH_SHORT).show()
            }

            builder.show()
        }

        myViewHolder.btnEditar.setOnClickListener {
            idProdutoAdm = prodId
            categoriaAdm = categoria

            val fragment = AdmEditarProdutoFragment()
            val transaction = (context as AdmHomeActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.containerAdm, fragment).addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int {
        return listQueerBox.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.produto_row, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var nome: TextView
        var btnEditar: Button
        var btnExcluir: Button

        init {
            img = itemView.findViewById(R.id.img)
            nome = itemView.findViewById(R.id.nome)
            btnEditar = itemView.findViewById(R.id.btnEditar)
            btnExcluir = itemView.findViewById(R.id.btnExcluir)
        }
    }
}