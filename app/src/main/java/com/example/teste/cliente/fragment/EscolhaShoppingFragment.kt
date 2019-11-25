package com.example.teste.cliente.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog

import com.example.teste.R
import com.example.teste.adapter.EscolhaShoppingAdapter
import com.example.teste.classe.*
import com.example.teste.model.*
import com.example.teste.model.Shopping
import com.example.teste.model.User
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.escolha_shopping.view.*
import kotlinx.android.synthetic.main.fragment_escolha_shopping.view.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */

class EscolhaShoppingFragment : Fragment() {

    private var listShoppings = arrayListOf<Shopping>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_escolha_shopping, container, false)

        infoCliente()

        preencherRecyclerView(view)

        return view
    }

    private fun infoCliente() {
        var ref2 = FirebaseDatabase.getInstance().getReference("users")
            .child(cpfUserLogado)
        val info2 = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                clienteNome = user?.nome.toString()
                clienteImg = user?.img.toString()
                clienteTel = user?.tel.toString()
            }
        }
        ref2.addListenerForSingleValueEvent(info2)
    }

    private fun preencherRecyclerView(view: View) {
        val shoppings = FirebaseDatabase.getInstance().getReference("shoppings")

        val adapter = EscolhaShoppingAdapter(activity!!, listShoppings)
        var recyclerView = view.recyclerViewEscolhaShopping
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        shoppings.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listShoppings.clear()

                    for (shopp in dataSnapshot.children) {
                        val s = shopp.getValue(Shopping::class.java)
                        listShoppings.add(s!!)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            })


    }




}
