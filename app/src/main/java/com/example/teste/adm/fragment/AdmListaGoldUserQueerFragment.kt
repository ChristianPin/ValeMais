package com.example.teste.adm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.teste.R
import com.example.teste.adapter.ListaGoldQueerAdapter
import com.example.teste.adapter.ListaGoldUserQueerAdapter
import com.example.teste.model.QueerBoxCaixa
import com.example.teste.model.UserModelQueer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_adm_lista_gold_queer.view.*
import kotlinx.android.synthetic.main.fragment_adm_lista_gold_user_queer.view.*

/**
 * A simple [Fragment] subclass.
 */
class AdmListaGoldUserQueerFragment : Fragment() {

    private var listUserQueer = arrayListOf<UserModelQueer>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_adm_lista_gold_user_queer, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val produtosQueerBox = FirebaseDatabase.getInstance().getReference("Queer Box Gold/Assinantes")

        val adapter = ListaGoldUserQueerAdapter(activity!!, listUserQueer)
        var recyclerView = view.recyclerViewListaGoldUserQueer
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        produtosQueerBox.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listUserQueer.clear()


                for (user in dataSnapshot.children) {
                    val u = user.getValue(UserModelQueer::class.java)
                    //Toast.makeText(context,user.key, Toast.LENGTH_SHORT).show()

                    listUserQueer.add(u!!)
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })
    }

}
