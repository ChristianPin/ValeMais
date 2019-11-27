package com.example.teste.cliente.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog

import com.example.teste.R
import com.example.teste.adapter.ComprasAdapter
import com.example.teste.classe.carrinho
import com.example.teste.classe.cpfUserLogado
import com.example.teste.model.Reserva
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.compras_row.view.*
import kotlinx.android.synthetic.main.fragment_compras.view.*

/**
 * A simple [Fragment] subclass.
 */
class ComprasFragment : Fragment() {

    private var listCompras = arrayListOf<Reserva>()
    lateinit var compras_text: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_compras, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val compras = FirebaseDatabase.getInstance().getReference("reservas").child(
            cpfUserLogado
        )

        val adapter = ComprasAdapter(activity!!, listCompras)
        var recyclerView = view.recyclerViewCompras
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        compras.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listCompras.clear()
                compras_text = view.compra_text.hint as String
                Log.i("DetalhesProduto", "COMPRAS ${compras_text}")

                for (res in dataSnapshot.children) {
                    val r = res.getValue(Reserva::class.java)
                    listCompras.add(r!!)
                    compras_text = "muda"
                }
                adapter.notifyDataSetChanged()

                if (compras_text == "ESSAS SÃO AS SUAS COMPRAS"){
                    SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Que vacilo!")
                        .setContentText("Sua carruagem está vazia! Vá à home para adquirir mimos.")
                        .setConfirmText("Ir à home")
                        .setConfirmClickListener {
                            val fragment = DestaquesFragment()
                            val fragmentManager = activity!!.supportFragmentManager
                            val fragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.container, fragment)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        }.show()
                }else{
                    Log.i("DetalhesProduto", "COMPRAS 1")
                    carrinho = "00,00"

                }
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })


    }
}