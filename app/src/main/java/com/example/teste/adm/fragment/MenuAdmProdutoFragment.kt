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
class MenuAdmProdutoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_adm_produto, container, false)

        val cardViewCadProduto = view.findViewById<View>(R.id.cardViewCadProduto) as CardView

        cardViewCadProduto.setOnClickListener {
            val fragment = AdmCadProdutoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val cardViewCatalogoProdutos = view.findViewById<View>(R.id.cardViewCatalogoProdutos) as CardView

        cardViewCatalogoProdutos.setOnClickListener {
            val fragment = ListaProdutoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }


}
