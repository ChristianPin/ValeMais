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
import com.example.teste.adm.fragment.ReservaClienteFragment
import com.example.teste.classe.clientes
import com.example.teste.classe.cpfClienteReserva
import com.example.teste.model.Reserva
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ReservasAdapter(private val context: Context, private val listReservas: ArrayList<Reserva>) :
    RecyclerView.Adapter<ReservasAdapter.MyViewHolder>() {

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
            shoppImg,
            recebido
        ) = listReservas[i]
        Log.d("ReservasFragment", "adapter: $clienteCpf")
        Picasso.get().load(listReservas[i].clienteImg).into(myViewHolder.img)
        myViewHolder.nome.text = clienteNome

        myViewHolder.btnVer.setOnClickListener {
            cpfClienteReserva = clienteCpf
            val fragment = ReservaClienteFragment()
            val transaction = (context as AdmHomeActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.containerAdm, fragment).addToBackStack(null).commit()
        }

        myViewHolder.btnEntregue.setOnClickListener {
            var ref = FirebaseDatabase.getInstance().getReference("reservas").child(clienteCpf)
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmação de entrega")
            builder.setMessage("Os produtos de $clienteNome foram entregues?")

            builder.setPositiveButton("Sim") { dialog, which ->
                Toast.makeText(context, "Entrega realizada!", Toast.LENGTH_SHORT).show()
                ref.removeValue()
            }

            builder.setNegativeButton("Não") { dialog, which ->
                Toast.makeText(context, "Entrega pendente!", Toast.LENGTH_SHORT).show()
            }

            builder.show()
        }
    }

    override fun getItemCount(): Int {
        return listReservas.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.cliente_row, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var nome: TextView
        var btnVer: Button
        var btnEntregue: Button

        init {
            img = itemView.findViewById(R.id.img)
            nome = itemView.findViewById(R.id.nome)
            btnVer = itemView.findViewById(R.id.btnVer)
            btnEntregue = itemView.findViewById(R.id.btnEntregue)
        }
    }
}