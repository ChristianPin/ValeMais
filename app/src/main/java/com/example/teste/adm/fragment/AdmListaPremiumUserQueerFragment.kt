package com.example.teste.adapter


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.teste.R

/**
 * A simple [Fragment] subclass.
 */
class AdmListaPremiumUserQueerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adm_lista_premium_user_queer, container, false)
    }


}
