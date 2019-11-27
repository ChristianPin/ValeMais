package com.example.teste.mensagem

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.R
import com.example.teste.model.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class ChatLogActivity : AppCompatActivity() {

    lateinit var recyclerViewConversa: RecyclerView
    lateinit var nomeTela : TextView

    companion object{
        val TAG = "Chatlog"
        val NOME_PERFIL = "NOME_PERFIL"
        val FOTO_PERFIL = "FOTO_PERFIL"
    }

    val adapter = GroupAdapter<ViewHolder>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        val nomeUser = intent.getStringExtra(NovaMensagemActivity.USER_KEY)
        val idUser = intent.getStringExtra(NovaMensagemActivity.KEY_USER)


        //val user = intent.getParcelableExtra<User>(NewMenssengeActivity.USER_KEY)

        nomeTela = findViewById(R.id.nomeChat)
        nomeTela.text = nomeUser
        //viewHolder.itemView.nomeChat.text = nomeUser

        recyclerViewConversa = findViewById(R.id.recyclerViewConversa)
        recyclerViewConversa.setLayoutManager(LinearLayoutManager(this))
        recyclerViewConversa.adapter = adapter

        //setupDammyData()
        listenForMessages()

        btnEnviarMensagem.setOnClickListener {
            Log.d(TAG, "Attempt to send message ola")
            performSendMessage()
            //val i = Intent(this, ResgatarSenhaActivity::class.java)
            //startActivity(i)
        }

        ver_perfil.setOnClickListener {
            val toUser = intent.getStringExtra(NovaMensagemActivity.KEY_USER_IMG)


            val i = Intent(this, PerfilFotoActivity::class.java)
            i.putExtra(PerfilFotoActivity.NOME_PERFIL, nomeUser)
            i.putExtra(PerfilFotoActivity.FOTO_PERFIL, toUser)

            startActivity(i)
        }

        ligar_button.setOnClickListener {
            val toUserTel = intent.getStringExtra(NovaMensagemActivity.KEY_USER_TEL)

            val i = Intent(Intent.ACTION_DIAL)
            i.data = Uri.parse("tel: 92"+toUserTel)
            startActivity(i)
        }

    }

    private fun listenForMessages(){
        val fromId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val toId = intent.getStringExtra(NovaMensagemActivity.KEY_USER)
        val ref = FirebaseDatabase.getInstance().getReference("/user-mensagens/$fromId/$toId")

        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)

                if(chatMessage != null){
                    Log.d(TAG, chatMessage?.text)

                    if(chatMessage.fromId == FirebaseAuth.getInstance().currentUser?.uid.toString()){
                        var imglogado = UltimasMensagensActivity.USER_KEY_IMG_LOGADO
                        adapter.add(ChatFromItem(chatMessage.text, imglogado, chatMessage.tempoEnvio))

                    }else{
                        val toUser = intent.getStringExtra(NovaMensagemActivity.KEY_USER_IMG)
                        adapter.add(ChatToItem(chatMessage.text, toUser, chatMessage.tempoEnvio))

                    }
                }
            }

            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot?) {

            }
        })
    }


    private fun performSendMessage(){

        val text = mensagemText.text.toString()


        val fromId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val toId = intent.getStringExtra(NovaMensagemActivity.KEY_USER)

        val today = Calendar.getInstance()
        var hora = SimpleDateFormat("H").format( today.time ).toInt()
        hora -= 4
        var minutos = SimpleDateFormat("m").format( today.time )

        val nome_toId = intent.getStringExtra(NovaMensagemActivity.USER_KEY)
        val foto_toId = intent.getStringExtra(NovaMensagemActivity.KEY_USER_IMG)
        val tel_toId = intent.getStringExtra(NovaMensagemActivity.KEY_USER_TEL)
        Log.d(TAG, "saved tel:$tel_toId")
        val ref = FirebaseDatabase.getInstance().getReference("/user-mensagens/$fromId/$toId").push()

        val refTo = FirebaseDatabase.getInstance().getReference("/user-mensagens/$toId/$fromId").push()

        val h_m = ""+hora+":"+minutos

        val chatMessage = ChatMessage(ref.key!!, text, fromId, toId, nome_toId, foto_toId, tel_toId, System.currentTimeMillis()/1000, h_m)
        ref.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "saved our chat message:${ref.key}")
                mensagemText.text.clear()
                recyclerViewConversa.scrollToPosition(adapter.itemCount -1)
            }

        refTo.setValue(chatMessage)

        val ref_latest_messages = FirebaseDatabase.getInstance().getReference("/latest-mensagens/$fromId/$toId")
        ref_latest_messages.setValue(chatMessage)

        val ref_to_latest_messages = FirebaseDatabase.getInstance().getReference("/latest-mensagens/$toId/$fromId")
        ref_to_latest_messages.setValue(chatMessage)
    }

}


class ChatFromItem(val text: String, val userImg: String, val tempo: String): Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textview_from_row.text = text

        //val url = userImg
        //val targetImageView = viewHolder.itemView.img_from_row
        //Picasso.get().load(url).into(targetImageView)

        viewHolder.itemView.tempo_from.text = tempo
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String, val userImg: String, val tempo: String): Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text

        //carrega a imagem do usuário
        val url = userImg
        val targetImageView = viewHolder.itemView.img_to_row
        Picasso.get().load(url).into(targetImageView)

        viewHolder.itemView.tempo_to.text = tempo


    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}
