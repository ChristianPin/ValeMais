package com.example.teste.adm.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.teste.R
import com.example.teste.adapter.ListaGoldQueerAdapter
import com.example.teste.adapter.ListaProdutoQueerBoxGoldAdapter
import com.example.teste.model.Produto
import com.example.teste.model.QueerBox
import com.example.teste.model.QueerBoxCaixa
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_adm_cad_queer_box.view.*
import kotlinx.android.synthetic.main.fragment_adm_lista_gold_queer.view.*

/**
 * A simple [Fragment] subclass.
 */
class AdmListaGoldQueerFragment : Fragment() {

    private var listQueerBox = arrayListOf<QueerBoxCaixa>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_adm_lista_gold_queer, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val produtosQueerBox = FirebaseDatabase.getInstance().getReference("Queer Box Gold/Caixas")

        val adapter = ListaGoldQueerAdapter(activity!!, listQueerBox)
        var recyclerView = view.recyclerViewListaProdutoQueerBoxGold
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        produtosQueerBox.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listQueerBox.clear()


                for (caixa in dataSnapshot.children) {
                    val c = caixa.getValue(QueerBoxCaixa::class.java)
                    Toast.makeText(context,caixa.key,Toast.LENGTH_SHORT).show()

                    listQueerBox.add(c!!)
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })
    }


}
