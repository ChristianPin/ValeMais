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
class AdmQueerBoxFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_adm_queer_box, container, false)

        val cardViewProdutosQueerBox = view.findViewById<View>(R.id.cardViewProdutosQueerBox) as CardView
        cardViewProdutosQueerBox.setOnClickListener {
            val fragment = ListaQueerBoxFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val cardViewCadQueerBox = view.findViewById<View>(R.id.cardViewCadQueerBox) as CardView
        cardViewCadQueerBox.setOnClickListener {
            val fragment = AdmAddQueerTipoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val cardViewListQueerBox = view.findViewById<View>(R.id.cardViewListaQueerBox) as CardView
        cardViewListQueerBox.setOnClickListener {
            val fragment = AdmQueerTipoListaFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }



        return view
    }
}
