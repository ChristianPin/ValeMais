package com.example.teste.cliente.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog

import com.example.teste.R
import com.example.teste.adapter.CarrinhoAdapter
import com.example.teste.adapter.EscolhaShoppingAdapter
import com.example.teste.classe.Produto
import com.example.teste.classe.carrinho
import com.example.teste.classe.cpfUserLogado
import com.example.teste.model.Carrinho
import com.example.teste.model.CarrinhoModel
import com.example.teste.model.ProdutoModel
import com.example.teste.model.UserModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.carrinho_row.view.*
import kotlinx.android.synthetic.main.fragment_carrinho.view.*

/**
 * A simple [Fragment] subclass.
 */
class CarrinhoFragment : Fragment() {

    private var listCarrinho = arrayListOf<Carrinho>()
    var total: Int = 0
    lateinit var total_car: String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_carrinho, container, false)

        preencherRecyclerView(view)


        return view
    }

    private fun preencherRecyclerView(view: View) {
        val carrinhoCliente = FirebaseDatabase.getInstance().getReference("carrinho").child(cpfUserLogado)

            val adapter = CarrinhoAdapter(activity!!, listCarrinho)
            var recyclerView = view.recyclerViewClienteCarrinho
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = adapter

            carrinhoCliente.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listCarrinho.clear()

                     total_car = view.total.hint as String


                    for (car in dataSnapshot.children) {
                        val c = car.getValue(Carrinho::class.java)
                        listCarrinho.add(c!!)
                            var preco = c.valor.toString().toInt()
                            var qtde = c.qtde.toString().toInt()
                            var mult = preco * qtde
                            total += mult
                            view.total.setText(total.toString())
                            total_car = total.toString()
                        Log.d("nome prod", "poxa2 ${total_car}")

                    }
                    adapter.notifyDataSetChanged()

                    if (total_car == "00,00" && carrinho =="00,00"){
                        SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Que vacilo!")
                            .setContentText("Sua carruagem está vazia! Vá à home para adquirir mimos.")
                            .setConfirmText("Ir à home")
                            .setConfirmClickListener {
                                val fragment = DestaquesFragment()
                                val fragmentManager = activity!!.supportFragmentManager
                                val fragmentTransaction = fragmentManager.beginTransaction()
                                fragmentTransaction.replace(R.id.container, fragment)
                                fragmentTransaction.addToBackStack(null)
                                fragmentTransaction.commit()
                            }.show()
                        view.total.setText("00,00")
                    }

                }



                override fun onCancelled(p0: DatabaseError?) {
                }



            })

            view.btnFinalizar.setOnClickListener {
                 //total_car = view.total.hint as String
                if(total_car == "00,00" && carrinho =="00,00"){
                    SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Que vacilo!")
                        .setContentText("Sua carruagem está vazia! Vá à home para adquirir mimos.")
                        .setConfirmText("Ir à home")
                        .setConfirmClickListener {
                            val fragment = DestaquesFragment()
                            val fragmentManager = activity!!.supportFragmentManager
                            val fragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.container, fragment)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        }.show()
                    //view.total.setText("00,00")
                }else{
                    val fragment = EscolhaShoppingFragment()
                    val fragmentManager = activity!!.supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.container, fragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                    carrinho = "00,00"
                    Log.d("nome prod", "total car 2 ${carrinho}")

                }
            }
        }
}
