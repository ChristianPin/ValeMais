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
class AdmAddQueerTipoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_adm_add_queer_tipo, container, false)

        val btnQueerP = view.findViewById<View>(R.id.cardViewQueerBoxP) as CardView
        btnQueerP.setOnClickListener {
            val fragment = AdmCadQueerBoxPremiumFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnQueerG = view.findViewById<View>(R.id.cardViewQueerBoxG) as CardView
        btnQueerG.setOnClickListener {
            val fragment = AdmCadQueerBoxGoldFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return view
    }


}
