package com.example.teste.adm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.teste.R
import com.example.teste.classe.cpfUserLogado
import com.example.teste.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_menu_adm.*

/**
 * A simple [Fragment] subclass.
 */
class MenuAdmFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_adm, container, false)

        val cardViewProduto = view.findViewById<View>(R.id.cardViewProduto) as CardView
        val cardViewShopping = view.findViewById<View>(R.id.cardViewShopping) as CardView
        val cardViewIndicacao = view.findViewById<View>(R.id.cardViewIndicacao) as CardView

        cardViewProduto.setOnClickListener {
            val fragment = MenuAdmProdutoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        cardViewShopping.setOnClickListener {
            val fragment = MenuAdmShoppingFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        cardViewIndicacao.setOnClickListener {
            val fragment = MenuAdmIndicacaoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        pesquisaCpf()

        return view
    }

    private fun pesquisaCpf() {
        var ref = FirebaseDatabase.getInstance().getReference("users")
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (us in dataSnapshot.children) {
                        val u = us.getValue(UserModel::class.java)
                        if (u?.userId.equals(FirebaseAuth.getInstance().currentUser!!.uid.toString())) {
                            cpfUserLogado = u?.cpf.toString()
                        }
                    }
                }
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }
}
