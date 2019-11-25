package com.example.teste.adm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.teste.R
import com.example.teste.adapter.ReservaClienteAdapter
import com.example.teste.classe.cpfClienteReserva
import com.example.teste.model.Reserva
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_reserva_cliente.view.*

/**
 * A simple [Fragment] subclass.
 */
class ReservaClienteFragment : Fragment() {

    private var listReservaCliente = arrayListOf<Reserva>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_reserva_cliente, container, false)

        val btnInfo = view.findViewById<View>(R.id.btnInfo) as CardView

        btnInfo.setOnClickListener {
            val fragment = ClienteInfoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnShopp = view.findViewById<View>(R.id.btnShopp) as CardView

        btnShopp.setOnClickListener {
            val fragment = ClienteShoppingFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val reservas = FirebaseDatabase.getInstance().getReference("reservas").child(
            cpfClienteReserva)

        val adapter = ReservaClienteAdapter(activity!!, listReservaCliente)
        var recyclerView = view.recyclerViewReservaCliente
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        reservas.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listReservaCliente.clear()

                for (child in dataSnapshot.children) {
                    val c = child.getValue(Reserva::class.java)
                    listReservaCliente.add(c!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })
    }
}
