package com.example.teste.adm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.teste.R
import com.example.teste.classe.cpfClienteReserva
import com.example.teste.model.Reserva
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_cliente_info.*
import kotlinx.android.synthetic.main.fragment_cliente_info.view.*
import kotlinx.android.synthetic.main.fragment_cliente_info.view.clienteImg

/**
 * A simple [Fragment] subclass.
 */
class ClienteInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cliente_info, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val clienteInfo = FirebaseDatabase.getInstance().getReference("reservas").child(
            cpfClienteReserva)

        clienteInfo.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (child in dataSnapshot.children) {
                    val c = child.getValue(Reserva::class.java)
                    view.clienteNome.setText(c?.clienteNome)
                    view.clienteTel.setText(c?.clienteTel)
                    Picasso.get().load(c?.clienteImg).into(clienteImg)
                }
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })
    }

}
