package com.example.teste.mensagem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.nova_mensagem_row.view.*

class NovaMensagemActivity : AppCompatActivity() {

    lateinit var recycleview_newmessage: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_mensagem)

        recycleview_newmessage = findViewById(R.id.recycleview_newmessage)
        recycleview_newmessage.setLayoutManager(LinearLayoutManager(this))

        fetchUsers()
    }


    companion object{
        val USER_KEY = "USER_KEY"
        val KEY_USER = "KEY_USER"
        val KEY_USER_IMG = "KEY_USER_IMG"
        val KEY_USER_TEL = "KEY_USER_TEL"
    }

    private fun fetchUsers() {
        val fromId = FirebaseAuth.getInstance().currentUser?.uid.toString()

        val ref = FirebaseDatabase.getInstance().getReference("users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach {
                    Log.d("Messenger", it.toString())
                    val user = it.getValue(UserModel::class.java)
                    Log.d("Messenger", "aaa: ${user?.cpf}")
                    if (user != null) {
                        if(fromId != user.userId){
                            adapter.add(UserItem(user))

                        }
                    }
                }

                adapter.setOnItemClickListener(OnItemClickListener { item, view ->

                    val userItem = item as UserItem

                    val i = Intent(view.context, ChatLogActivity::class.java)
                    i.putExtra(USER_KEY, userItem.user.nome)
                    i.putExtra(KEY_USER, userItem.user.userId)
                    i.putExtra(KEY_USER_IMG, userItem.user.img)
                    i.putExtra(KEY_USER_TEL, userItem.user.tel)
                    //i.putExtra(USER_KEY, userItem.user)

                    startActivity(i)

                    finish()
                })

                recycleview_newmessage.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })
    }
}

class UserItem(val user: UserModel) : Item<ViewHolder>() {
    val fromId = FirebaseAuth.getInstance().currentUser?.uid.toString()


    override fun bind(viewHolder: ViewHolder, position: Int) {


        viewHolder.itemView.nome.text = user.nome
        Picasso.get().load(user.img).into(viewHolder.itemView.img)
        layout



    }

    override fun getLayout(): Int {

        return R.layout.nova_mensagem_row
    }
}

