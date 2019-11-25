package com.example.teste.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.model.Shopping
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ShoppingAdapter(private val context: Context, private val listShoppings: ArrayList<Shopping>): RecyclerView.Adapter<ShoppingAdapter.MyViewHolder>(){

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var(shoppId, nome, endereco, horaInicio, horaFim, dia, img) = listShoppings[i]

        Picasso.get().load(listShoppings[i].img).into(myViewHolder.img)
        myViewHolder.nome.text = nome
        myViewHolder.endereco.text = endereco
        myViewHolder.dia.text = dia
        myViewHolder.horaInicio.text = horaInicio
        myViewHolder.horaFim.text = horaFim
    }

    override fun getItemCount(): Int {
        return listShoppings.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.shopping_cliente, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var nome: TextView
        var endereco: TextView
        var dia: TextView
        var horaInicio: TextView
        var horaFim: TextView

        init {
            img = itemView.findViewById(R.id.img)
            nome = itemView.findViewById(R.id.nome)
            endereco = itemView.findViewById(R.id.endereco)
            dia = itemView.findViewById(R.id.dia)
            horaInicio = itemView.findViewById(R.id.horaInicio)
            horaFim = itemView.findViewById(R.id.horaFim)
        }
    }
}