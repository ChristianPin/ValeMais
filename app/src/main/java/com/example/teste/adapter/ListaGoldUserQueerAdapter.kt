package com.example.teste.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.adm.activity.AdmHomeActivity
import com.example.teste.adm.fragment.AdmListaGoldUserQueerFragment
import com.example.teste.adm.fragment.ListaGoldProdutosQueerFragment
import com.example.teste.classe.id_caixa_produto
import com.example.teste.model.QueerBoxCaixa
import com.example.teste.model.User
import com.example.teste.model.UserModelQueer
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ListaGoldUserQueerAdapter (
    private val context: Context,
    private val listUserQueer: ArrayList<UserModelQueer>
) : RecyclerView.Adapter<ListaGoldUserQueerAdapter.MyViewHolder>() {


    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var (userId, nome, cpf, tel,nasc, email, senha, img, diaPaga, nomeCaixa) = listUserQueer[i]


        Picasso.get().load(img).into(myViewHolder.img)
        myViewHolder.nome.text = nome
        myViewHolder.dataAss.text = diaPaga


    }



    override fun getItemCount(): Int {
        return listUserQueer.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.lista_gold_user_queer_row, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var nome: TextView
        var cardViewGold: CardView
        var dataAss: TextView

        init {
            img = itemView.findViewById(R.id.imageViewUser)
            nome = itemView.findViewById(R.id.textView_nome)
            cardViewGold = itemView.findViewById(R.id.cardViewGoldUser)
            dataAss = itemView.findViewById(R.id.textView_data_assinatura)
        }
    }
}