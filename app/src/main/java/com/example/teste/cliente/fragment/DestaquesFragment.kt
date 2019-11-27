package com.example.teste.cliente.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.adapter.ProdutoAdapter
import com.example.teste.classe.*
import com.example.teste.model.Produto
import com.example.teste.model.ProdutoModel
import com.example.teste.model.UserModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_destaques.view.*
import kotlinx.android.synthetic.main.produtos_home.*
import kotlinx.android.synthetic.main.produtos_home.view.*
import kotlinx.android.synthetic.main.produtos_home.view.btnDetalhes

/**
 * A simple [Fragment] subclass.
 */
class DestaquesFragment : Fragment() {

    private var listProdutos = arrayListOf<Produto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_destaques, container, false)

        pesquisaCpf()

        preencherRecyclerView(view)

        val btnBanner = view.findViewById<View>(R.id.btnBanner) as CardView

        btnBanner.setOnClickListener {
            val fragment = ShoppingFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }

    private fun pesquisaCpf() {
        var ref = FirebaseDatabase.getInstance().getReference("users")
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (us in dataSnapshot.children) {
                        val u = us.getValue(UserModel::class.java)
                        if (u?.userId.equals(FirebaseAuth.getInstance().currentUser!!.uid.toString())) {
                            cpfUserLogado = u?.cpf.toString()
                            queer_cpf_cliente = u?.cpf.toString()
                            queer_email_cliente = u?.email.toString()
                            queer_img_cliente = u?.img.toString()
                            queer_nasc_cliente = u?.nasc.toString()
                            queer_nome_cliente = u?.nome.toString()
                            queer_senha_cliente = u?.senha.toString()
                            queer_tel_cliente = u?.tel.toString()
                            queer_userId_cliente = u?.userId.toString()
                        }
                    }
                }
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

    lateinit var idSelecionado: String
    private fun preencherRecyclerView(view: View) {
        val produtos = FirebaseDatabase.getInstance().getReference("produtos")

        val adapter = ProdutoAdapter(activity!!, listProdutos)
        var recyclerView = view.recyclerViewHome
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