package com.example.teste.adm.fragment


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.teste.R
import com.example.teste.classe.Shopping
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_adm_cad_shopping.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AdmCadShoppingFragment : Fragment() {

    lateinit var addNome: EditText
    lateinit var addEnd : EditText
    lateinit var addHoraInicio: EditText
    lateinit var addHoraFim : EditText
    lateinit var addDia : EditText
    lateinit var img: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_adm_cad_shopping, container, false)

        addNome = view.findViewById(R.id.addNome)
        addEnd = view.findViewById(R.id.addEnd)
        addHoraInicio = view.findViewById(R.id.addHoraInicio)
        addHoraFim = view.findViewById(R.id.addHoraFim)
        addDia = view.findViewById(R.id.addDia)

        val btnUpload = view.findViewById<View>(R.id.btnUpload) as Button
        btnUpload.setOnClickListener {
            Log.d("AdmCadShoppingFragment", "Clicou botão upload")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val btnCadastrar = view.findViewById<View>(R.id.btnCadastrar) as Button
        btnCadastrar.setOnClickListener {
            cadastrarShopping()
            val fragment = ListaShoppingFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnCancelar = view.findViewById<View>(R.id.btnCancelar) as Button
        btnCancelar.setOnClickListener {
            val fragment = MenuAdmShoppingFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }

    var uriFotoSelecionada : Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Log.d("AdmCadShoppingFragment", "Foto selecionada")

            uriFotoSelecionada = data.data

            Log.d("AdmCadShoppingFragment", "URI: $uriFotoSelecionada")

            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uriFotoSelecionada)

            circleImageView.setImageBitmap(bitmap)
            btnUpload.alpha = 0f

            uploadImagem()
            //val bitmapDrawable = BitmapDrawable(bitmap)
            //btnUpload.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun uploadImagem(){
        if(uriFotoSelecionada == null) return
        val nomeImg = UUID.randomUUID().toString()
        Log.d("AdmCadShoppingFragment", "Nome img: $nomeImg")
        val ref = FirebaseStorage.getInstance().getReference("/shoppings/$nomeImg")

        ref.putFile(uriFotoSelecionada!!).addOnSuccessListener {
            Log.d("AdmCadShoppingFragment", "Imagem salva com sucesso: ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                Log.d("AdmCadShoppingFragment", "Location: $it")
                img = it.toString()
            }
        }
    }

    lateinit var shoppId: String

    private fun cadastrarShopping(){
        val nome = addNome.text.toString().trim()
        val end = addEnd.text.toString().trim()
        val horaInicio = addHoraInicio.text.toString().trim()
        val horaFim = addHoraFim.text.toString().trim()
        val dia = addDia.text.toString().trim()

        if (nome.isEmpty() || end.isEmpty() || horaInicio.isEmpty() || horaFim.isEmpty() || dia.isEmpty() || uriFotoSelecionada == null){
            Toast.makeText(activity, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        }else {
            val ref = FirebaseDatabase.getInstance().getReference("shoppings")
            shoppId = ref.push().key.toString()
            val shopping =
                Shopping(shoppId, nome, end, horaInicio, horaFim, dia, img)
            ref.child(shoppId).setValue(shopping).addOnCompleteListener {
                Log.d("AdmCadShoppingFragment", "Shopping cadastrado com sucesso!")
                Toast.makeText(
                    activity,
                    "Shopping cadastrado com sucesso!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}
