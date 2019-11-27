package com.example.teste.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.adm.activity.AdmHomeActivity
import com.example.teste.adm.fragment.AdmAjustesFragment
import com.example.teste.adm.fragment.AdmListaGoldUserQueerFragment
import com.example.teste.adm.fragment.ListaGoldProdutosQueerFragment
import com.example.teste.adm.fragment.ListaQueerBoxFragment
import com.example.teste.classe.id_caixa_produto
import com.example.teste.classe.queer_adapter_gold
import com.example.teste.cliente.activity.HomeActivity
import com.example.teste.cliente.fragment.ComprasFragment
import com.example.teste.model.Produto
import com.example.teste.model.QueerBox
import com.example.teste.model.QueerBoxCaixa
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ListaGoldQueerAdapter (
    private val context: Context,
    private val listQueerBox: ArrayList<QueerBoxCaixa>
) : RecyclerView.Adapter<ListaGoldQueerAdapter.MyViewHolder>() {


    var tipo: String = "gold"
    lateinit var prodIdCad: String

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var (id, nome, qtde, img,tipo) = listQueerBox[i]

//
//        Picasso.get().load(img).into(myViewHolder.img)
        myViewHolder.nome.text = nome

        myViewHolder.btnSelecionar.setOnClickListener {
            Toast.makeText(context, id,Toast.LENGTH_SHORT).show()
            id_caixa_produto = id

            val fragment = ListaGoldProdutosQueerFragment()
            val transaction = (context as AdmHomeActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.containerAdm, fragment).addToBackStack(null).commit()
        }

        myViewHolder.btnEnviar.setOnClickListener {
            //Toast.makeText(context, id,Toast.LENGTH_SHORT).show()
            val fragment = AdmListaGoldUserQueerFragment()
            val transaction = (context as AdmHomeActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.containerAdm, fragment).addToBackStack(null).commit()
        }
    }




    override fun getItemCount(): Int {
        return listQueerBox.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.adm_lista_gold_queer_row, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var nome: TextView
        var btnSelecionar: Button
        var btnEnviar: Button
        var cardViewGold: CardView

        init {
            img = itemView.findViewById(R.id.imgQ)
            nome = itemView.findViewById(R.id.nome)
            btnSelecionar = itemView.findViewById(R.id.btnSelecionar)
            btnEnviar = itemView.findViewById(R.id.btnEnviar)
            cardViewGold = itemView.findViewById(R.id.cardViewListaGoldQueer)
        }
    }
}