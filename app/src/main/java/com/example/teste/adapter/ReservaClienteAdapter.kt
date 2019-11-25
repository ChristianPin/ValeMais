package com.example.teste.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.model.Reserva
import com.squareup.picasso.Picasso

class ReservaClienteAdapter (private val context: Context, private val listReservaCliente: ArrayList<Reserva>) :
    RecyclerView.Adapter<ReservaClienteAdapter.MyViewHolder>() {

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var (clienteCpf,
            clienteNome,
            clienteImg,
            clienteTel,
            prodId,
            prodNome,
            prodDesc,
            prodTam,
            prodQtde,
            prodValor,
            prodCategoria,
            prodImg,
            shoppId,
            shoppNome,
            shoppEndereco,
            shoppHoraInicio,
            shoppHoraFim,
            shoppDia,
            shoppImg
        ) = listReservaCliente[i]

        Picasso.get().load(listReservaCliente[i].prodImg).into(myViewHolder.prodImg)
        myViewHolder.prodQtde.text = prodQtde
        myViewHolder.prodTam.text = prodTam
        myViewHolder.prodValor.text = prodValor
    }

    override fun getItemCount(): Int {
        return listReservaCliente.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.reserva_cliente, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var prodImg: ImageView
        var prodQtde: TextView
        var prodTam: TextView
        var prodValor: TextView

        init {
            prodImg = itemView.findViewById(R.id.prodImg)
            prodQtde = itemView.findViewById(R.id.prodQtde)
            prodTam = itemView.findViewById(R.id.prodTam)
            prodValor = itemView.findViewById(R.id.prodValor)
        }
    }
}