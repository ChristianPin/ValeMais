package com.example.teste.adm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

import com.example.teste.R
import kotlinx.android.synthetic.main.fragment_menu_adm_shopping.*

/**
 * A simple [Fragment] subclass.
 */
class MenuAdmShoppingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_adm_shopping, container, false)

        val cardViewCadShopping = view.findViewById<View>(R.id.cardViewCadShopping) as CardView
        val cardViewListaShoppings = view.findViewById<View>(R.id.cardViewListaShoppings) as CardView

        cardViewCadShopping.setOnClickListener {
            val fragment = AdmCadShoppingFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        cardViewListaShoppings.setOnClickListener {
            val fragment = ListaShoppingFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }


}
