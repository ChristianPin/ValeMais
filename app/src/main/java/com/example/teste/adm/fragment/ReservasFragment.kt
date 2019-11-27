package com.example.teste.adm.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.teste.R
import com.example.teste.adapter.ReservasAdapter
import com.example.teste.classe.clientes
import com.example.teste.classe.cpfClienteReserva
import com.example.teste.classe.cpfUserLogado
import com.example.teste.model.Reserva
import com.example.teste.model.ReservaModel
import com.example.teste.model.User
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cliente_row.view.*
import kotlinx.android.synthetic.main.fragment_reservas.view.*

/**
 * A simple [Fragment] subclass.
 */
class ReservasFragment : Fragment() {

    private var listReservas = arrayListOf<Reserva>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_reservas, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val reservas = FirebaseDatabase.getInstance().getReference("reservas")

        val adapter = ReservasAdapter(activity!!, listReservas)
        var recyclerView = view.recyclerViewReservas
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        reservas.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //listReservas.clear()

                for (child in dataSnapshot.children) {
                    listReservas.clear()
                    var cpfUserReserva = child.key
                    clientes = arrayOf(cpfUserReserva)
                    Log.d("ReservasFragment", "pegando cpf reservas: $cpfUserReserva")

                    for(cli in clientes!!) {
                        //listReservas.clear()
                        Log.d("ReservasFragment", "array: $cli")

                        var reservas2 = FirebaseDatabase.getInstance().getReference("reservas").child(cli)
                        reservas2.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                //listReservas.clear()
                                Log.d("ReservasFragment", "entrou")

                                for (child in dataSnapshot.children) {
                                    val c = child.getValue(Reserva::class.java)
                                    listReservas.add(c!!)
                                    Log.d("ReservasFragment", "nome cliente dentro children: ${c.clienteNome}")
                                    Log.d("ReservasFragment", "dia dentro children: ${c.shoppDia}")

                                }
                                adapter.notifyDataSetChanged()
                            }

                            override fun onCancelled(p0: DatabaseError?) {
                            }
                        })
                        adapter.notifyDataSetChanged()
                    }
                    adapter.notifyDataSetChanged()
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })
        adapter.notifyDataSetChanged()
    }
}
