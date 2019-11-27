package com.example.teste.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.classe.cpfUserLogado
import com.example.teste.model.Carrinho
import com.example.teste.model.Produto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class CarrinhoAdapter(private val context: Context, private val listCarrinho: ArrayList<Carrinho>) :
    RecyclerView.Adapter<CarrinhoAdapter.MyViewHolder>() {

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var (clienteCpf, prodId, nome, desc, tam, qtde, valor, categoria, img) = listCarrinho[i]

        Picasso.get().load(listCarrinho[i].img).into(myViewHolder.img)
        myViewHolder.qtde.text = qtde
        myViewHolder.valor.text = valor

        myViewHolder.btnRemover.setOnClickListener {
            var qtdeSolicitada = qtde.toString().toInt()
            var ref = FirebaseDatabase.getInstance().getReference("carrinho").child(cpfUserLogado)
            var ref1 = FirebaseDatabase.getInstance().getReference("produtos").child(prodId)
            val info = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val p = dataSnapshot.getValue(Produto::class.java)
                    var qtdeProd = p?.qtde.toString().toInt()
                    var qtdeFinal = qtdeSolicitada + qtdeProd
                    val produtoEstoque = com.example.teste.classe.Produto(p?.prodId.toString(), p?.nome.toString(), p?.desc.toString(), p?.tam.toString(), qtdeFinal.toString(), p?.valor.toString(), p?.categoria.toString(), p?.img.toString())
                    ref1.setValue(produtoEstoque)
                    ref.child(prodId).removeValue()
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            }
            ref1.addListenerForSingleValueEvent(info)
        }
    }

    override fun getItemCount(): Int {
        return listCarrinho.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.carrinho_row, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var qtde: TextView
        var valor: TextView
        var btnRemover: Button

        init {
            img = itemView.findViewById(R.id.img)
            qtde = itemView.findViewById(R.id.qtde)
            valor = itemView.findViewById(R.id.valor)
            btnRemover = itemView.findViewById(R.id.btnRemover)
        }
    }

}