package com.example.teste.mensagem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.classe.cpfUserLogado
import com.example.teste.model.ChatMessage
import com.example.teste.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_ultimas_mensagens.*
import kotlinx.android.synthetic.main.ultimas_mensagens_row.view.*

class UltimasMensagensActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var recyclerViewLatestMessages: RecyclerView



    companion object{
        var currentUser: UserModel? = null
        lateinit var USER_KEY_IMG_LOGADO: String
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimas_mensagens)

        recyclerViewLatestMessages = findViewById(R.id.recyclerViewLatestMessages)
        recyclerViewLatestMessages.setLayoutManager(LinearLayoutManager(this))
        recyclerViewLatestMessages.adapter = adapter


        nova_mensagem.setOnClickListener {
            val i = Intent(this, NovaMensagemActivity::class.java)
            startActivity(i)
        }

        //ação do adapter
        adapter.setOnItemClickListener{item, view ->

            val i = Intent(this, ChatLogActivity::class.java)



            val row = item as LatestMessageRow

            if(row.chatMessage.fromId == FirebaseAuth.getInstance().currentUser?.uid){

                i.putExtra(NovaMensagemActivity.KEY_USER, row.chatMessage.toId)
                i.putExtra(NovaMensagemActivity.USER_KEY, row.chatMessage.nomeToId)
                i.putExtra(NovaMensagemActivity.KEY_USER_IMG, row.chatMessage.imgToId)
                i.putExtra(NovaMensagemActivity.KEY_USER_TEL, row.chatMessage.telToId)
                startActivity(i)
            }else{
                val ref = FirebaseDatabase.getInstance().getReference("/users/")

                ref.addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(p0: DataSnapshot) {
                        val adapter = GroupAdapter<ViewHolder>()

                        p0.children.forEach {
                            //Log.d("Messenger", it.toString())
                            val user = it.getValue(UserModel::class.java)
                            if(user?.userId == row.chatMessage.fromId){
                                Log.d("Messenger", "aaa_adapter: ${user?.cpf}")

                                i.putExtra(NovaMensagemActivity.KEY_USER, user?.userId)
                                i.putExtra(NovaMensagemActivity.USER_KEY, user?.nome)
                                i.putExtra(NovaMensagemActivity.KEY_USER_IMG,user?.img)
                                i.putExtra(NovaMensagemActivity.KEY_USER_TEL,user?.tel)

                                startActivity(i)

                                //chatPartnerId = chatMessage.toId
                                //Log.d("TAG NOME", "chat: ${chatMessage.nomeToId}")


                            }
                            //if (user != null) {
                            //          adapter.add(UserItem(user))
                            // }
                        }


                    }

                    override fun onCancelled(p0: DatabaseError?) {
                    }
                })


            }


        }

        //setupDummyRows()

        listenForLatestMessages()

        fetchCurrentUser()

    }


    class LatestMessageRow(val chatMessage: ChatMessage): Item<ViewHolder>(){

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.textView_ultima_mensagem.text = chatMessage.text

            val chatPartnerId: String
            if(chatMessage.fromId == FirebaseAuth.getInstance().currentUser?.uid){
                chatPartnerId = chatMessage.toId
                viewHolder.itemView.textView_nome.text = chatMessage.nomeToId
                Log.d("TAG NOME", "chat: ${chatMessage.nomeToId}")

                val targetImageView = viewHolder.itemView.imageViewLatestMessages
                Picasso.get().load(chatMessage.imgToId).into(targetImageView)

            }else{
                val fromId = FirebaseAuth.getInstance().currentUser?.uid.toString()

                val ref = FirebaseDatabase.getInstance().getReference("/users/")

                ref.addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(p0: DataSnapshot) {
                        val adapter = GroupAdapter<ViewHolder>()

                        p0.children.forEach {
                            Log.d("Messenger", it.toString())
                            val user = it.getValue(UserModel::class.java)
                            Log.d("Messenger", "aaaok: ${user?.cpf}")
                            if(user?.userId == chatMessage.fromId){

                                //chatPartnerId = chatMessage.toId
                                viewHolder.itemView.textView_nome.text = user?.nome
                                Log.d("TAG NOME", "chat: ${chatMessage.nomeToId}")

                                val targetImageView = viewHolder.itemView.imageViewLatestMessages
                                Picasso.get().load(user?.img).into(targetImageView)

                            }
                            //if (user != null) {
                            //          adapter.add(UserItem(user))
                            // }
                        }


                    }

                    override fun onCancelled(p0: DatabaseError?) {
                    }
                })


            }


        }

        override fun getLayout(): Int {
            return R.layout.ultimas_mensagens_row
        }
    }

    val latestMessagesMap = HashMap<String, ChatMessage>()

    private fun refreshRecycleViewMessages(){
        adapter.clear()
        latestMessagesMap.values.forEach{
            adapter.add(LatestMessageRow(it))
        }
    }

    private fun listenForLatestMessages(){
        val fromId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        Log.d("Chat Message", "chat: $fromId")


        val ref = FirebaseDatabase.getInstance().getReference("/latest-mensagens/$fromId")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)?: return

                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecycleViewMessages()

                //adapter.add(LatestMessageRow(chatMessage))
                Log.d("Chat Message", "chat: $chatMessage")

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)?: return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecycleViewMessages()
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

            override fun onCancelled(p0: DatabaseError) {
            }


        })


    }

    val adapter = GroupAdapter<ViewHolder>()


    private fun fetchCurrentUser() {
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
                            Log.d("LatestMessages", "img user0: $cpfUserLogado")
                            var ref2 = FirebaseDatabase.getInstance().getReference("users").child(
                                cpfUserLogado
                            )
                            val info2 = object : ValueEventListener {
                                override fun onCancelled(databaseError: DatabaseError) {
                                    //erro
                                }

                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    val user = dataSnapshot.getValue(UserModel::class.java)
                                    var imagem = user?.img.toString()
                                    USER_KEY_IMG_LOGADO = imagem
                                    Log.d("LatestMessages", "img user: $imagem")
                                    //nome_usuario.text = user?.nome
                                    //email_usuario.text = user?.email
                                    //Picasso.get().load(user?.img).into(foto_usuario)
                                }
                            }
                            ref2.addListenerForSingleValueEvent(info2)
                        }
                    }
                }
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }


}
