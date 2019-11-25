package com.example.teste.cliente.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teste.R
import com.example.teste.adapter.SerieAdapter
import com.example.teste.model.Indicacao
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_serie.*

class SerieActivity : AppCompatActivity() {

    private var listSeries = arrayListOf<Indicacao>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie)

        preencherRecyclerView()
    }

    private fun preencherRecyclerView() {
        val series = FirebaseDatabase.getInstance().getReference("indicacoes")

        val adapter = SerieAdapter(this, listSeries)
        var recyclerView = recyclerViewSerie
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        series.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listSeries.clear()

                    for (serie in dataSnapshot.children) {
                        val s = serie.getValue(Indicacao::class.java)
                        listSeries.add(s!!)
                    }

                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            }
        )
    }
}
