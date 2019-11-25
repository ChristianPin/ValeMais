package com.example.teste.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.teste.R
import com.example.teste.classe.queer_adapter
import com.example.teste.classe.queer_adapter_premium
import com.example.teste.model.Produto
import com.example.teste.model.QueerBox
import com.example.teste.model.QueerBoxCaixa
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ListaProdutoQueerBoxPremiumAdapter (
    private val context: Context,
    private val listQueerBox: ArrayList<Produto>
) : RecyclerView.Adapter<ListaProdutoQueerBoxPremiumAdapter.MyViewHolder>() {

    var tipo: String = "premium"
    lateinit var prodIdCad: String


    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var (prodId, nome, desc, tam, qtde, valor, categoria, img) = listQueerBox[i]

        queer_adapter_premium += 1

        Picasso.get().load(img).into(myViewHolder.img)
        myViewHolder.nome.text = nome

        myViewHolder.btnRemover.setOnClickListener {

        }

        myViewHolder.btnSelecionar.setOnClickListener {

            if(queer_adapter_premium == 1){
                val produtos = FirebaseDatabase.getInstance().getReference("Queer Box Premium/Caixas/Caixa $queer_adapter_premium")
                prodIdCad = produtos.push().key.toString()
                val produtosQueerBox = FirebaseDatabase.getInstance().getReference("Queer Box Premium/Produtos/Caixa $queer_adapter_premium")


                val caixa = QueerBoxCaixa(
                    "${queer_adapter_premium}",
                    "Caixa ${queer_adapter_premium}",
                    "10",
                    "",
                    tipo
                )

                produtos.setValue(caixa).addOnCompleteListener{

                }

                val produto = QueerBox(
                    prodId,
                    nome,
                    desc,
                    tam,
                    valor,
                    qtde,
                    categoria,
                    img,
                    tipo,
                    queer_adapter_premium.toString()
                )


                produtosQueerBox.child(produto.prodId).setValue(produto).addOnCompleteListener{
                    val mAlertDialog = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                    mAlertDialog.setTitle("Produto Adicionado")
                    mAlertDialog.setCanceledOnTouchOutside(true)
                    mAlertDialog.hideConfirmButton()
                    mAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"))
                    mAlertDialog.getProgressHelper().setProgress(maxOf(5f,1000f))
                    mAlertDialog.show()
                    Log.d("AdmCadProdutoFragment", "Produto cadastrado com sucesso!")
                }
            }else{
                val produtos = FirebaseDatabase.getInstance().getReference("Queer Box Premium/Caixas/Caixa $queer_adapter_premium")
                prodIdCad = produtos.push().key.toString()
                val produtosQueerBox = FirebaseDatabase.getInstance().getReference("Queer Box Premium/Produtos/Caixa $queer_adapter_premium")


                val caixa = QueerBoxCaixa(
                    "$queer_adapter_premium",
                    "Caixa $queer_adapter_premium",
                    "10",
                    "",
                    tipo
                )

                produtos.setValue(caixa).addOnCompleteListener{

                }

                val produto = QueerBox(
                    prodId,
                    nome,
                    desc,
                    tam,
                    valor,
                    qtde,
                    categoria,
                    img,
                    tipo,
                    queer_adapter_premium.toString()
                )


                produtosQueerBox.child(produto.prodId).setValue(produto).addOnCompleteListener{
                    val mAlertDialog = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                    mAlertDialog.setTitle("Produto Adicionado")
                    mAlertDialog.setCanceledOnTouchOutside(true)
                    mAlertDialog.hideConfirmButton()
                    mAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"))
                    mAlertDialog.getProgressHelper().setProgress(maxOf(5f,1000f))
                    mAlertDialog.show()

                    Log.d("AdmCadProdutoFragment", "Produto cadastrado com sucesso!")
                }
            }
            Log.d("Vamos la", "global $queer_adapter")



        }
    }

    override fun getItemCount(): Int {
        return listQueerBox.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.produto_queer_box_row, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var nome: TextView
        var btnSelecionar: Button
        var btnRemover: Button

        init {
            img = itemView.findViewById(R.id.imgQ)
            nome = itemView.findViewById(R.id.nome)
            btnSelecionar = itemView.findViewById(R.id.btnSelecionar)
            btnRemover = itemView.findViewById(R.id.btnRemover)
        }
    }
}