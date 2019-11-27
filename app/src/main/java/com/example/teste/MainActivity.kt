package com.example.teste
import android.content.Intent
//import android.support.v4.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.teste.adm.activity.AdmHomeActivity
import com.example.teste.cliente.activity.HomeActivity
import com.example.teste.mensagem.NovaMensagemActivity
import com.example.teste.model.UserModel
import com.github.paolorotolo.appintro.AppIntro
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class MainActivity : AppIntro() {

    private var preferenceHelper: PreferenceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main);
        val auth = FirebaseAuth.getInstance().currentUser

        if(auth == null) {
            preferenceHelper = PreferenceHelper(this)

            if (preferenceHelper!!.getIntro().equals("no")) {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            }

            addSlide(IntroFragment1())  //extend AppIntro and comment setContentView
            addSlide(IntroFragment2())
            addSlide(IntroFragment3())
            addSlide(IntroFragment4())
        }else{
            val idUser = FirebaseAuth.getInstance().currentUser?.uid
            val ref = FirebaseDatabase.getInstance().getReference("/users/")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(p0: DataSnapshot) {
                    val adapter = GroupAdapter<ViewHolder>()

                    p0.children.forEach {
                        //Log.d("Messenger", it.toString())
                        val user = it.getValue(UserModel::class.java)
                        if(user?.userId == idUser){
                            if(user?.senha == "adm12345"){
                                startActivity(Intent(baseContext, AdmHomeActivity::class.java))
                            }else{
                                startActivity(Intent(baseContext, HomeActivity::class.java))
                            }
                            Log.d("Messenger", "aaa_adapter: ${user?.cpf}")
                        }
                    }


                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            })

        }
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)

        preferenceHelper!!.putIntro("no")

        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)

        preferenceHelper!!.putIntro("no")

        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
        // Do something when the slide changes.
    }

}