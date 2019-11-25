package com.example.teste.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.classe.idProdSelecionado
import com.example.teste.cliente.activity.HomeActivity
import com.example.teste.cliente.fragment.DetalhesProdutoFragment
import com.example.teste.model.Produto
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ProdutoAdapter(private val context: Context, private val listProdutos: ArrayList<Produto>): RecyclerView.Adapter<ProdutoAdapter.MyViewHolder>(){

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var(prodId, nome, desc, tam, qtde, valor, categoria, img) = listProdutos[i]

        if(qtde.equals("0")){
            var excluir = FirebaseDatabase.getInstance().getReference("produtos").child(prodId)
            excluir.removeValue()
        }

        Picasso.get().load(listProdutos[i].img).into(myViewHolder.img)
        myViewHolder.nome.text = nome
        myViewHolder.valor.text = valor

        myViewHolder.btnDetalhes.setOnClickListener {
            idProdSelecionado = prodId
            val fragment = DetalhesProdutoFragment()
            val transaction = (context as HomeActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment).addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int {
        return listProdutos.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.produtos_home, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var nome: TextView
        var valor: TextView
        var btnDetalhes: Button

        init {
            img = itemView.findViewById(R.id.img)
            nome = itemView.findViewById(R.id.nome)
            valor = itemView.findViewById(R.id.valor)
            btnDetalhes = itemView.findViewById(R.id.btnDetalhes)
        }
    }
}