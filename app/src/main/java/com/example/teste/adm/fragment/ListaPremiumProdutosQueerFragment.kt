package com.example.teste.adm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.teste.R
import com.example.teste.adapter.ListaPremiumProdutosQueerAdapter
import com.example.teste.classe.id_caixa_produto_premium
import com.example.teste.model.QueerBox
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_lista_premium_produtos_queer.view.*

/**
 * A simple [Fragment] subclass.
 */
class ListaPremiumProdutosQueerFragment : Fragment() {

    private var listQueerBox = arrayListOf<QueerBox>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View =  inflater.inflate(R.layout.fragment_lista_premium_produtos_queer, container, false)

        return view
    }


    private fun preencherRecyclerView(view: View) {

        val produtosQueerBox = FirebaseDatabase.getInstance().getReference("Queer Box Premium/Produtos/Caixa 2")

        val adapter = ListaPremiumProdutosQueerAdapter(activity!!, listQueerBox)
        var recyclerView = view.recyclerViewListaPremiumProdutoQueerBox
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        produtosQueerBox.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listQueerBox.clear()


                for (prod in dataSnapshot.children) {
                    val p = prod.getValue(QueerBox::class.java)
                    //Toast.makeText(context,prod.key,Toast.LENGTH_SHORT).show()

                    listQueerBox.add(p!!)
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })
    }


}
