package com.example.teste.cliente.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.cliente.fragment.*
import com.example.teste.model.ProdutoModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_row.view.*


class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    lateinit var mSearchText: EditText
    lateinit var mRecyclerView: RecyclerView

    lateinit var mDatabase : DatabaseReference

    lateinit var FirebaseRecyclerAdapter : FirebaseRecyclerAdapter<ProdutoModel, ProdutosViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navigationView = findViewById(R.id.bottom_nav) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener(this@HomeActivity)

        //Instancia o Fragment que fica aberto por padrão
        val destaquesFragment = DestaquesFragment()
        //passa esse fragment por parametro
        abrirFragment(destaquesFragment)

        mSearchText = findViewById(R.id.barraBusca)
        mRecyclerView = findViewById(R.id.recyclerViewPesquisa)
        mDatabase = FirebaseDatabase.getInstance().getReference("produtos")

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))

        mSearchText.addTextChangedListener(object  : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val searchText = mSearchText.getText().toString().trim()

                loadFirebaseData(searchText)
            }
        } )
    }

    private fun loadFirebaseData(searchText : String) {

        if(searchText.isEmpty()){

            FirebaseRecyclerAdapter.cleanup()
            mRecyclerView.adapter = FirebaseRecyclerAdapter
            Log.d("nome prod", "eu to aqui oh n escreveu nada")

        }else {


            val firebaseSearchQuery = mDatabase.orderByChild("nome").startAt(searchText).endAt(searchText + "\uf8ff")

            FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<ProdutoModel , ProdutosViewHolder>(

                ProdutoModel::class.java,
                R.layout.search_row,
                ProdutosViewHolder::class.java,
                firebaseSearchQuery

            ) {
                override fun populateViewHolder(viewHolder: ProdutosViewHolder, model: ProdutoModel?, position: Int) {

                    viewHolder.mView.nomeSearch.setText(model?.nome)
                    Log.d("nome prod", "eu to aqui oh"+model?.nome)
                    //Picasso.with(this).load(model?.img).into(viewHolder.mView.imgSearch)
                    Picasso.get().load(model?.img).into(viewHolder.mView.imgSearch)
                }

            }

            mRecyclerView.adapter = FirebaseRecyclerAdapter

        }
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId.equals(R.id.navigation_home)){
            val destaquesFragment = DestaquesFragment()
            abrirFragment(destaquesFragment)
            Toast.makeText(this@HomeActivity, "Destaques", Toast.LENGTH_SHORT).show()
        }else{
            if(item.itemId.equals(R.id.navigation_box)){
                Toast.makeText(this@HomeActivity, "Queer Box", Toast.LENGTH_SHORT).show()
                val queerBoxFragment = QueerBoxFragment()
                abrirFragment(queerBoxFragment)
            }else{
                if(item.itemId.equals(R.id.navigation_carrinho)){
                    Toast.makeText(this@HomeActivity, "Carrinho", Toast.LENGTH_SHORT).show()
                    val carrinhoFragment = CarrinhoFragment()
                    abrirFragment(carrinhoFragment)
                }else{
                    val ajustesFragment = AjustesFragment()
                    abrirFragment(ajustesFragment)
                    Toast.makeText(this@HomeActivity, "Conta", Toast.LENGTH_SHORT).show()

                }
            }
        }
        return true
    }

    fun abrirFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    class ProdutosViewHolder(var mView: View) : RecyclerView.ViewHolder(mView) {

    }
}
