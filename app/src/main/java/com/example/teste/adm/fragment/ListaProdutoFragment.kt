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
import com.example.teste.adapter.ListaProdutoAdapter
import com.example.teste.model.Produto
import com.example.teste.model.ProdutoModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_lista_produto.view.*
import kotlinx.android.synthetic.main.produto_row.*
import kotlinx.android.synthetic.main.produto_row.view.*
import kotlinx.android.synthetic.main.produto_row.view.btnEditar
import kotlinx.android.synthetic.main.produto_row.view.img
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ListaProdutoFragment : Fragment() {

    private var listProdutos = arrayListOf<com.example.teste.model.Produto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_lista_produto, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val produtos = FirebaseDatabase.getInstance().getReference("produtos")

        val adapter = ListaProdutoAdapter(activity!!, listProdutos)
        var recyclerView = view.recyclerViewListaProduto
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        produtos.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listProdutos.clear()

                    for (prod in dataSnapshot.children) {
                        val p = prod.getValue(Produto::class.java)
                        listProdutos.add(p!!)
                    }

                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            }
        )
    }
}