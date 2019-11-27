package com.example.teste.cliente.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.teste.R
import com.example.teste.model.ProdutoModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.produtos_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class RoupasFragment : Fragment() {

    //lateinit var ref : DatabaseReference
    //lateinit var recyclerViewClienteRoupa : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_roupas, container, false)

        /*ref = FirebaseDatabase.getInstance().getReference("produtos")
        recyclerViewClienteRoupa = view.findViewById(R.id.recyclerViewClienteRoupa)
        recyclerViewClienteRoupa.setHasFixedSize(true)
        recyclerViewClienteRoupa.layoutManager = LinearLayoutManager(activity)

        preencherRecyclerView()*/

        return view
    }

    /*private fun preencherRecyclerView() {
        val FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<ProdutoModel, RoupaViewHolder>(
            ProdutoModel::class.java,
            R.layout.produtos_home,
            RoupaViewHolder::class.java,
            ref
        ){
            override fun populateViewHolder(viewHolder: RoupaViewHolder, model: ProdutoModel?, pos: Int) {
                if(model?.categoria.equals("Roupa")) {
                    viewHolder.view.nome.setText(model?.nome)
                    viewHolder.view.valor.setText(model?.valor)
                    Picasso.get().load(model?.img).into(viewHolder.view.img)

                    viewHolder.view.btnDetalhes.setOnClickListener {
                        val prodId = model?.prodId
                        Log.d("RoupasFragment", "Produto selecionado: $prodId")

                        val fragment = DetalhesProdutoFragment()
                        val fragmentManager = activity!!.supportFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.container, fragment)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }
                }
            }
        }

        recyclerViewClienteRoupa.adapter = FirebaseRecyclerAdapter
    }

    class RoupaViewHolder(var view : View) : RecyclerView.ViewHolder(view){

    }*/
}
