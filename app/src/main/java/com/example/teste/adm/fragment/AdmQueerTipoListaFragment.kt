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
class AdmQueerTipoListaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_queer_adm_tipo_lista, container, false)

        val cardViewProdutosQueerBoxP = view.findViewById<View>(R.id.cardViewQueerBoxP) as CardView
        cardViewProdutosQueerBoxP.setOnClickListener {
            val fragment = AdmListaPremiumQueerFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val cardViewProdutosQueerBoxG = view.findViewById<View>(R.id.cardViewQueerBoxG) as CardView
        cardViewProdutosQueerBoxG.setOnClickListener {
            val fragment = AdmListaGoldQueerFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        return view
    }


}
