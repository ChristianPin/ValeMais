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
class AcessoriosFragment : Fragment() {

    //lateinit var ref : DatabaseReference
    //lateinit var recyclerViewClienteAcessorio : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_acessorios, container, false)

        /*ref = FirebaseDatabase.getInstance().getReference("produtos")
        recyclerViewClienteAcessorio = view.findViewById(R.id.recyclerViewClienteAcessorio)
        recyclerViewClienteAcessorio.setHasFixedSize(true)
        recyclerViewClienteAcessorio.layoutManager = LinearLayoutManager(activity)

        preencherRecyclerView()*/

        return view
    }

    /*private fun preencherRecyclerView() {
        val FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<ProdutoModel, AcessorioViewHolder>(
            ProdutoModel::class.java,
            R.layout.produtos_home,
            AcessorioViewHolder::class.java,
            ref
        ){
            override fun populateViewHolder(viewHolder: AcessorioViewHolder, model: ProdutoModel?, pos: Int) {
                if(model?.categoria.equals("Acessório")) {
                    viewHolder.view.nome.setText(model?.nome)
                    viewHolder.view.valor.setText(model?.valor)
                    Picasso.get().load(model?.img).into(viewHolder.view.img)

                    viewHolder.view.btnDetalhes.setOnClickListener {
                        val prodId = model?.prodId
                        Log.d("AcessoriosFragment", "Produto selecionado: $prodId")

                        val fragment = DetalhesProdutoFragment()
                        val fragmentManager = activity!!.supportFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.container, fragment)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()

                        /*val i = Intent(activity, CliProdDesc::class.java)
                    i.putExtra("produto", prodId)
                    startActivity(i)*/
                    }
                }
            }
        }

        recyclerViewClienteAcessorio.adapter = FirebaseRecyclerAdapter
    }

    class AcessorioViewHolder(var view : View) : RecyclerView.ViewHolder(view){

    }*/

}
