package com.example.teste
//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    private var btn: Button? = null
    private var btn2: Button? = null

    private var preferenceHelper: PreferenceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        btn = findViewById(R.id.btn) as Button
        btn2 = findViewById(R.id.btnEntrar) as Button

        preferenceHelper = PreferenceHelper(this)

        btn!!.setOnClickListener {
            preferenceHelper!!.putIntro("")
            onBackPressed()
        }

        btn2!!.setOnClickListener {
            preferenceHelper!!.putIntro("no")

            val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this.finish()
        }

    }

}