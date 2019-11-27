package com.example.teste.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.model.Indicacao
import com.squareup.picasso.Picasso

class SerieAdapter(
    private val context: Context,
    private val listSeries: ArrayList<Indicacao>
) : RecyclerView.Adapter<SerieAdapter.MyViewHolder>() {

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var (indicId, nome, desc, img) = listSeries[i]

        Picasso.get().load(listSeries[i].img).into(myViewHolder.img)
        myViewHolder.addNome.text = nome
        myViewHolder.addDesc.text = desc
    }

    override fun getItemCount(): Int {
        return listSeries.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.serie_row, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var addDesc: TextView
        var addNome: TextView

        init {
            img = itemView.findViewById(R.id.img)
            addDesc = itemView.findViewById(R.id.addDesc)
            addNome = itemView.findViewById(R.id.addNome)
        }
    }
}