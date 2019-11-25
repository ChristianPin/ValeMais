package com.example.teste.cliente.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.teste.R
import com.example.teste.classe.*
import com.example.teste.model.UserModel
import com.example.teste.model.UserModelQueer
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass.
 */
class QueerBoxDetalhesCaixasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_queer_box_detalhes_caixas, container, false)

        val btnPremium = view.findViewById<View>(R.id.btnCaixaP) as Button
        btnPremium.setOnClickListener {
            val fragment = QueerBoxDetalhesCadUserPremiumFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnGold = view.findViewById<View>(R.id.btnCaixaG) as Button
        btnGold.setOnClickListener {
            val fragment = QueerBoxDetalhesCadUserGoldFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }



}
