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
import com.example.teste.model.Reserva
import com.squareup.picasso.Picasso

class ComprasAdapter(
    private val context: Context,
    private val listCompras: ArrayList<Reserva>
) : RecyclerView.Adapter<ComprasAdapter.MyViewHolder>() {

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var (clienteCpf, clienteNome, clienteImg, clienteTel, prodId, prodNome, prodDesc, prodTam, prodQtde, prodValor, prodCategoria, prodImg, shoppId, shoppNome, shoppEndereco, shoppHoraInicio, shoppHoraFim, shoppDia, shoppImg, recebido) = listCompras[i]

        Picasso.get().load(listCompras[i].prodImg).into(myViewHolder.img)
        myViewHolder.valor.text = prodValor
        myViewHolder.recebido.text = recebido
    }

    override fun getItemCount(): Int {
        return listCompras.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.compras_row, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var valor: TextView
        var recebido: TextView

        init {
            img = itemView.findViewById(R.id.img)
            valor = itemView.findViewById(R.id.valor)
            recebido = itemView.findViewById(R.id.recebido)
        }
    }
}