package com.example.teste.adm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

import com.example.teste.R

/**
 * A simple [Fragment] subclass.
 */
class MenuAdmIndicacaoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_adm_indicacao, container, false)

        val cardViewCadIndicacao = view.findViewById<View>(R.id.cardViewCadIndicacao) as CardView

        cardViewCadIndicacao.setOnClickListener {
            val fragment = AdmCadIndicacaoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val cardViewCatalogoIndicacoes = view.findViewById<View>(R.id.cardViewCatalogoIndicacoes) as CardView

        cardViewCatalogoIndicacoes.setOnClickListener {
            val fragment = ListaIndicacaoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }


}
