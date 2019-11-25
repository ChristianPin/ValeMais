package com.example.teste.adapter

import android.content.Context
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
import com.example.teste.adm.fragment.ListaGoldProdutosQueerFragment
import com.example.teste.classe.id_caixa_produto
import com.example.teste.model.QueerBox
import com.squareup.picasso.Picasso

class ListaGoldProdutosQueerAdapter(
    private val context: Context,
    private val listQueerBox: ArrayList<QueerBox>
) : RecyclerView.Adapter<ListaGoldProdutosQueerAdapter.MyViewHolder>() {


    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var (prodId, nome, desc, tam, qtde, valor, categoria, img, tipo,id) = listQueerBox[i]

//
        Picasso.get().load(img).into(myViewHolder.img)
        myViewHolder.nome.text = nome



        myViewHolder.btnSelecionar.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return listQueerBox.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.lista_gold_produtos_queer_row, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var nome: TextView
        var btnSelecionar: Button

        init {
            img = itemView.findViewById(R.id.imgQ)
            nome = itemView.findViewById(R.id.nome)
            btnSelecionar = itemView.findViewById(R.id.btnSelecionar)
        }
    }
}