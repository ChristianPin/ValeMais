package com.example.teste.adm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.teste.R
import com.example.teste.adapter.ListaQueerBoxAdapter
import com.example.teste.model.Produto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_lista_queer_box.view.*

/**
 * A simple [Fragment] subclass.
 */
class ListaQueerBoxFragment : Fragment() {

    private var listQueerBox = arrayListOf<Produto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_lista_queer_box, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val produtosQueerBox = FirebaseDatabase.getInstance().getReference("queer box")

        val adapter = ListaQueerBoxAdapter(activity!!, listQueerBox)
        var recyclerView = view.recyclerViewListaQueerBox
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        produtosQueerBox.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listQueerBox.clear()

                for (prod in dataSnapshot.children) {
                    val p = prod.getValue(Produto::class.java)
                    listQueerBox.add(p!!)
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })
    }
}