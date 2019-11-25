package com.example.teste.adm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.teste.R
import com.example.teste.adapter.ListaIndicacaoAdapter
import com.example.teste.model.Indicacao
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_lista_indicacao.view.*

/**
 * A simple [Fragment] subclass.
 */
class ListaIndicacaoFragment : Fragment() {

    private var listIndicacoes = arrayListOf<Indicacao>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lista_indicacao, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val indicacoes = FirebaseDatabase.getInstance().getReference("indicacoes")

        val adapter = ListaIndicacaoAdapter(activity!!, listIndicacoes)
        var recyclerView = view.recyclerViewListaIndicacao
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        indicacoes.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listIndicacoes.clear()

                    for (indic in dataSnapshot.children) {
                        val i = indic.getValue(Indicacao::class.java)
                        listIndicacoes.add(i!!)
                    }

                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            }
        )
    }

}
