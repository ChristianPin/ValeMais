package com.example.teste.adm.fragment


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.teste.R
import com.example.teste.adapter.ListaShoppingAdapter
import com.example.teste.classe.Shopping
import com.example.teste.model.ShoppingModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_lista_shopping.view.*
import kotlinx.android.synthetic.main.shopping_row.view.*
import kotlinx.android.synthetic.main.shopping_row.view.img
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ListaShoppingFragment : Fragment() {

    private var listShoppings = arrayListOf<com.example.teste.model.Shopping>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_lista_shopping, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val shoppings = FirebaseDatabase.getInstance().getReference("shoppings")

        val adapter = ListaShoppingAdapter(activity!!, listShoppings)
        var recyclerView = view.recyclerViewListaShopping
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        shoppings.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listShoppings.clear()

                    for (shopp in dataSnapshot.children) {
                        val s = shopp.getValue(com.example.teste.model.Shopping::class.java)
                        listShoppings.add(s!!)
                    }

                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            }
        )
    }
}