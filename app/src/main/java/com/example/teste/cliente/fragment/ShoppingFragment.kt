package com.example.teste.cliente.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.teste.R
import com.example.teste.adapter.ShoppingAdapter
import com.example.teste.model.Shopping
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_shopping.view.*
import kotlinx.android.synthetic.main.shopping_row.view.*

/**
 * A simple [Fragment] subclass.
 */
class ShoppingFragment : Fragment() {

    private var listShoppings = arrayListOf<Shopping>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_shopping, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val shoppings = FirebaseDatabase.getInstance().getReference("shoppings")

        val adapter = ShoppingAdapter(activity!!, listShoppings)
        var recyclerView = view.recyclerViewShopping
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
            }
        )
    }
}