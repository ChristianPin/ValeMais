package com.example.teste.cliente.fragment


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import cn.pedant.SweetAlert.SweetAlertDialog

import com.example.teste.R
import com.example.teste.classe.*
import com.example.teste.model.UserModelQueer
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_queer_box_detalhes_cad_user_gold.*

/**
 * A simple [Fragment] subclass.
 */
class QueerBoxDetalhesCadUserPremiumFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view:View = inflater.inflate(R.layout.fragment_queer_box_detalhes_cad_user_premium, container, false)

        val btnSalvar = view.findViewById<View>(R.id.btnSalvar) as Button
        btnSalvar.setOnClickListener {
            assinarCaixaPremium(view)
        }

        val btnCancelar = view.findViewById<View>(R.id.btnCancelar) as Button
        btnCancelar.setOnClickListener {
            val fragment = QueerBoxDetalhesFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }

    fun assinarCaixaPremium(view: View){
        val nomeC = nomeG.text.toString().trim()
        val diaAss = diaAssG.text.toString().trim()



        val assinantesQueerBox = FirebaseDatabase.getInstance().getReference("Queer Box Premium/Assinantes")


        val usuario = UserModelQueer(
            queer_userId_cliente,
            queer_nome_cliente,
            queer_cpf_cliente,
            queer_tel_cliente,
            queer_nasc_cliente,
            queer_email_cliente,
            queer_senha_cliente,
            queer_img_cliente,
            diaAss,
            nomeC
        )

        assinantesQueerBox.child(queer_cpf_cliente).setValue(usuario).addOnCompleteListener{
            val mAlertDialog = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
            mAlertDialog.setTitle("Assinatura efetuda")
            mAlertDialog.setContentText("Aguarde a mensagem de nossos administradores")
            mAlertDialog.setCanceledOnTouchOutside(true)
            mAlertDialog.hideConfirmButton()
            mAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"))
            mAlertDialog.getProgressHelper().setProgress(maxOf(5f,1000f))
            mAlertDialog.show()
            val fragment = QueerBoxDetalhesFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }


}
