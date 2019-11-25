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
import android.widget.*
import android.widget.ArrayAdapter
import com.example.teste.R
import com.example.teste.adm.activity.AdmHomeActivity
import com.example.teste.classe.Produto
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_adm_cad_produto.*
import kotlinx.android.synthetic.main.fragment_adm_cad_produto.view.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class AdmCadProdutoFragment : Fragment() {

    lateinit var addNome: EditText
    lateinit var addDesc: EditText
    lateinit var addTam: EditText
    lateinit var addQtde: EditText
    lateinit var addValor: EditText
    lateinit var categoria: String
    lateinit var img: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_adm_cad_produto, container, false)

        addNome = view.findViewById(R.id.addNome)
        addDesc = view.findViewById(R.id.addDesc)
        addTam = view.findViewById(R.id.addTam)
        addQtde = view.findViewById(R.id.addQtde)
        addValor = view.findViewById(R.id.addValor)

        val categorias = arrayOf(
            "Roupa",
            "Acessório",
            "Queer box"
        )
        view.spinnerCat.adapter = ArrayAdapter<String>(
            activity!!.applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            categorias
        )
        view.spinnerCat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(
                    activity, "Selecione uma categoria!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                categoria = categorias.get(p2)
                Log.d("AdmCadProdutoFragment", "Categoria: $categoria")
            }
        }

        val btnUpload = view.findViewById<View>(R.id.btnUpload) as Button
        btnUpload.setOnClickListener {
            Log.d("AdmCadProdutoFragment", "Clicou botão upload")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val btnCancelar = view.findViewById<View>(R.id.btnCancelar) as Button
        btnCancelar.setOnClickListener {
            val fragment = MenuAdmProdutoFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containerAdm, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnCadastrar = view.findViewById<View>(R.id.btnCadastrar) as Button
        btnCadastrar.setOnClickListener {
            cadastrarProduto()

            if(categoria.equals("Queer box")){
                val fragment = ListaQueerBoxFragment()
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.containerAdm, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }else{
                val fragment = ListaProdutoFragment()
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.containerAdm, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        return view
    }

    var uriFotoSelecionada: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("AdmCadProdutoFragment", "Foto selecionada")

            uriFotoSelecionada = data.data

            Log.d("AdmCadProdutoFragment", "URI: $uriFotoSelecionada")

            val bitmap =
                MediaStore.Images.Media.getBitmap(activity?.contentResolver, uriFotoSelecionada)

            circleImageView.setImageBitmap(bitmap)
            btnUpload.alpha = 0f

            uploadImagem()
            //val bitmapDrawable = BitmapDrawable(bitmap)
            //btnUpload.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun uploadImagem() {
        if (uriFotoSelecionada == null) return
        val nomeImg = UUID.randomUUID().toString()
        Log.d("AdmCadProdutoFragment", "Nome img: $nomeImg")
        val ref = FirebaseStorage.getInstance().getReference("/produtos/$nomeImg")

        ref.putFile(uriFotoSelecionada!!).addOnSuccessListener {
            Log.d("AdmCadProdutoFragment", "Imagem salva com sucesso: ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                Log.d("AdmCadProdutoFragment", "Location: $it")
                img = it.toString()
            }
        }
    }

    lateinit var prodId: String

    private fun cadastrarProduto() {
        val nome = addNome.text.toString().trim()
        val desc = addDesc.text.toString().trim()
        val tam = addTam.text.toString().trim()
        val valor = addValor.text.toString().trim()
        val qtde = addQtde.text.toString().trim()

        if (nome.isEmpty() || desc.isEmpty() || tam.isEmpty() || valor.isEmpty() || qtde.isEmpty() || uriFotoSelecionada == null) {
            Toast.makeText(activity, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        } else {
            if (categoria.equals("Queer box")) {
                val ref = FirebaseDatabase.getInstance().getReference("queer box")
                prodId = ref.push().key.toString()
                val produto = Produto(
                    prodId,
                    nome,
                    desc,
                    tam,
                    valor,
                    qtde,
                    categoria,
                    img
                )
                ref.child(prodId).setValue(produto).addOnCompleteListener {
                    Log.d("AdmCadProdutoFragment", "Produto cadastrado com sucesso!")
                    Toast.makeText(
                        activity,
                        "Produto cadastrado com sucesso!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                val ref = FirebaseDatabase.getInstance().getReference("produtos")
                prodId = ref.push().key.toString()
                val produto = Produto(
                    prodId,
                    nome,
                    desc,
                    tam,
                    valor,
                    qtde,
                    categoria,
                    img
                )
                ref.child(prodId).setValue(produto).addOnCompleteListener {
                    Log.d("AdmCadProdutoFragment", "Produto cadastrado com sucesso!")
                    Toast.makeText(
                        activity,
                        "Produto cadastrado com sucesso!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}