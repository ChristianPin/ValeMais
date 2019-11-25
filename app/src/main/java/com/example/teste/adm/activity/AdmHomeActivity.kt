package com.example.teste.adm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.MainActivity
import com.example.teste.adm.fragment.MenuAdmFragment
import com.example.teste.R
import com.example.teste.adm.fragment.AdmAjustesFragment
import com.example.teste.adm.fragment.AdmQueerBoxFragment
import com.example.teste.adm.fragment.ReservasFragment
import com.example.teste.cliente.activity.HomeActivity
import com.example.teste.model.ProdutoModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.search_adm_row.view.*
import kotlinx.android.synthetic.main.search_row.view.*

class AdmHomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener  {

    private lateinit var auth: FirebaseAuth

    //PESQUISAR PRODUTO
    lateinit var mSearchText: EditText
    lateinit var mRecyclerView: RecyclerView
    lateinit var mDatabase : DatabaseReference
    lateinit var btn_search : ImageButton
    lateinit var FirebaseRecyclerAdapter : FirebaseRecyclerAdapter<ProdutoModel, ProdutosViewHolder>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adm_home)

        val navigationView = findViewById(R.id.bottom_nav) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener(this@AdmHomeActivity)

        //Instancia o Fragment que fica aberto por padrão
        val menuAdmFragment = MenuAdmFragment()
        //passa esse fragment por parametro
        abrirFragment(menuAdmFragment)


        mSearchText = findViewById(R.id.barraBuscaAdm)
        mRecyclerView = findViewById(R.id.recyclerViewPesquisaAdm)
        mDatabase = FirebaseDatabase.getInstance().getReference("produtos")
        btn_search = findViewById(R.id.btn_pesquisar_adm)



        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))

            mSearchText.addTextChangedListener(object  : TextWatcher {

                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


                        val searchText = mSearchText.getText().toString().trim()

                    //btn_search.setOnClickListener{
                        loadFirebaseData(searchText)

                   // }


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
                R.layout.search_adm_row,
                ProdutosViewHolder::class.java,
                firebaseSearchQuery

            ) {
                override fun populateViewHolder(viewHolder: ProdutosViewHolder, model: ProdutoModel?, position: Int) {

                    viewHolder.mView.nomeSearchAdm.setText(model?.nome)
                    Log.d("nome prod", "eu to aqui oh"+model?.nome)
                    //Picasso.with(this).load(model?.img).into(viewHolder.mView.imgSearch)
                    Picasso.get().load(model?.img).into(viewHolder.mView.imgSearchAdm)
                }

            }

            mRecyclerView.adapter = FirebaseRecyclerAdapter

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId.equals(R.id.navigation_produtos)){
            //Toast.makeText(this@AdmHomeActivity, "Menu", Toast.LENGTH_SHORT).show()
            val menuAdmFragment = MenuAdmFragment()
            //passa esse fragment por parametro
            abrirFragment(menuAdmFragment)
        }else{
            if(item.itemId.equals(R.id.navigation_box)){
                //Toast.makeText(this@AdmHomeActivity, "Queer Box", Toast.LENGTH_SHORT).show()
                val admQueerBoxFragment = AdmQueerBoxFragment()
                //passa esse fragment por parametro
                abrirFragment(admQueerBoxFragment)
            }else{
                if(item.itemId.equals(R.id.navigation_reservas)){
                    //Toast.makeText(this@AdmHomeActivity, "Reservas", Toast.LENGTH_SHORT).show()
                    val reservasFragment = ReservasFragment()
                    //passa esse fragment por parametro
                    abrirFragment(reservasFragment)
                }else{
                    //Toast.makeText(this@AdmHomeActivity, "Ajustes", Toast.LENGTH_SHORT).show()
                    val ajustesFragment = AdmAjustesFragment()
                    //passa esse fragment por parametro
                    abrirFragment(ajustesFragment)
                }
            }
        }
        return true
    }

    fun abrirFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerAdm, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    class ProdutosViewHolder(var mView: View) : RecyclerView.ViewHolder(mView) {

    }
}
