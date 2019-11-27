package com.example.teste.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.teste.R
import com.example.teste.classe.*
import com.example.teste.cliente.activity.HomeActivity
import com.example.teste.cliente.fragment.CarrinhoFragment
import com.example.teste.cliente.fragment.ComprasFragment
import com.example.teste.cliente.fragment.DestaquesFragment
import com.example.teste.model.Carrinho
import com.example.teste.model.Produto
import com.example.teste.model.Reserva
import com.example.teste.model.Shopping
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.ArrayList

class EscolhaShoppingAdapter(
    private val context: Context,
    private val listShoppings: ArrayList<Shopping>
) : RecyclerView.Adapter<EscolhaShoppingAdapter.MyViewHolder>() {



    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {


        var (shoppId, nome, endereco, horaInicio, horaFim, dia, img) = listShoppings[i]

        Picasso.get().load(listShoppings[i].img).into(myViewHolder.img)
        myViewHolder.nome.text = nome
        myViewHolder.endereco.text = endereco
        myViewHolder.dia.text = dia
        myViewHolder.horaInicio.text = horaInicio
        myViewHolder.horaFim.text = horaFim



        myViewHolder.btnLugar.setOnClickListener {
            var ref1 = FirebaseDatabase.getInstance().getReference("carrinho").child(cpfUserLogado)
            val info = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val car = dataSnapshot.getValue(Carrinho::class.java)

                    if (dataSnapshot.exists()) {
                        for (prod in dataSnapshot.children) {
                            val p = prod.getValue(Produto::class.java)
                            var recebido = "não"
                            val reserva = Reserva(
                                cpfUserLogado,
                                clienteNome,
                                clienteImg,
                                clienteTel,
                                p?.prodId.toString(),
                                p?.nome.toString(),
                                p?.desc.toString(),
                                p?.tam.toString(),
                                p?.qtde.toString(),
                                p?.valor.toString(),
                                p?.categoria.toString(),
                                p?.img.toString(),
                                shoppId.toString(),
                                nome.toString(),
                                endereco.toString(),
                                horaInicio.toString(),
                                horaFim.toString(),
                                dia.toString(),
                                img.toString(),
                                recebido
                            )
                            var ref2 = FirebaseDatabase.getInstance().getReference("reservas")
                            ref2.child(cpfUserLogado).child(p?.prodId).setValue(reserva)

                            ref1.removeValue()
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            }
            ref1.addListenerForSingleValueEvent(info)

            val mAlertDialog = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
            mAlertDialog.setTitle("Compra Efetuada")
            mAlertDialog.setContentText("Aguarde a mensagem de nossos administradores")
            mAlertDialog.setCanceledOnTouchOutside(true)
            mAlertDialog.hideConfirmButton()
            mAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"))
            mAlertDialog.getProgressHelper().setProgress(maxOf(5f,1000f))
            Log.i("DetalhesProduto", "PAROU: ${mAlertDialog.getProgressHelper().getProgress()}")
            mAlertDialog.show()

            if(mAlertDialog.getProgressHelper().getProgress() == 1000f){
                Log.i("DetalhesProduto", "PRODUTO parou: pena")

                //mAlertDialog.dismissWithAnimation()

//                startActivity(Intent(this, HomeActivity::class.java))
//                val fragment = DestaquesFragment()
//                val fragmentManager = activity!!.supportFragmentManager
//                val fragmentTransaction = fragmentManager.beginTransaction()
//                fragmentTransaction.replace(R.id.container, fragment)
//                fragmentTransaction.addToBackStack(null)
//                fragmentTransaction.commit()
//                mAlertDialog.dismissWithAnimation()
                carrinho = "2"
                val fragment = ComprasFragment()
                val transaction = (context as HomeActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container, fragment).addToBackStack(null).commit()
            }
        }
    }

    override fun getItemCount(): Int {
        return listShoppings.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.escolha_shopping, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var nome: TextView
        var endereco: TextView
        var dia: TextView
        var horaInicio: TextView
        var horaFim: TextView
        var btnLugar: Button

        init {
            img = itemView.findViewById(R.id.img)
            nome = itemView.findViewById(R.id.nome)
            endereco = itemView.findViewById(R.id.endereco)
            dia = itemView.findViewById(R.id.dia)
            horaInicio = itemView.findViewById(R.id.horaInicio)
            horaFim = itemView.findViewById(R.id.horaFim)
            btnLugar = itemView.findViewById(R.id.btnLugar)
        }
    }
}
