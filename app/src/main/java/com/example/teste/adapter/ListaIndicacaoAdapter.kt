package com.example.teste.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.adm.activity.AdmHomeActivity
import com.example.teste.adm.fragment.AdmEditarIndicacaoFragment
import com.example.teste.classe.idIndicacaoAdm
import com.example.teste.model.Indicacao
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ListaIndicacaoAdapter(
    private val context: Context,
    private val listIndicacoes: ArrayList<Indicacao>
) : RecyclerView.Adapter<ListaIndicacaoAdapter.MyViewHolder>() {

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        var (indicId, nome, desc, img) = listIndicacoes[i]

        Picasso.get().load(listIndicacoes[i].img).into(myViewHolder.img)
        myViewHolder.nome.text = nome

        myViewHolder.btnExcluir.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("indicacoes")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmação de exclusão")
            builder.setMessage("Deseja realmente excluir: $nome?")
            Log.d("ExclusaoIndic", "ID PRODUTO: $indicId")

            builder.setPositiveButton("Sim") { dialog, which ->
                Toast.makeText(context, "Indicação excluída!", Toast.LENGTH_SHORT).show()
                ref.child(indicId).removeValue()
            }

            builder.setNegativeButton("Não") { dialog, which ->
                Toast.makeText(context, "Exclusão cancelada!", Toast.LENGTH_SHORT).show()
            }

            builder.show()
        }

        myViewHolder.btnEditar.setOnClickListener {
            idIndicacaoAdm = indicId

            val fragment = AdmEditarIndicacaoFragment()
            val transaction = (context as AdmHomeActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.containerAdm, fragment).addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int {
        return listIndicacoes.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.indicacao_row, viewGroup, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var nome: TextView
        var btnEditar: Button
        var btnExcluir: Button

        init {
            img = itemView.findViewById(R.id.img)
            nome = itemView.findViewById(R.id.nome)
            btnEditar = itemView.findViewById(R.id.btnEditar)
            btnExcluir = itemView.findViewById(R.id.btnExcluir)
        }
    }
}