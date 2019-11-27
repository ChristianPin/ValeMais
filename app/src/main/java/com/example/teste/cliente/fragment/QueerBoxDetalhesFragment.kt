package com.example.teste.cliente.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.teste.R

/**
 * A simple [Fragment] subclass.
 */
class QueerBoxDetalhesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_queer_box_detalhes, container, false)

        val btnMais = view.findViewById<View>(R.id.btnAssinar) as Button
        btnMais.setOnClickListener {
            val fragment = QueerBoxDetalhesCaixasFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }


}
