package com.example.MovieApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splash1.*

class splash1 : AppCompatActivity() {

    lateinit var pref: sharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash1)

        pref= sharedPref(this)
        if(pref.getValue("noSplash").equals("1")){
            startActivity(Intent(this,SignIn::class.java))
            finishAffinity()
        }
        buttonNext1.setOnClickListener {
            startActivity(Intent(this,splash2::class.java))
            finishAffinity()
        }
        buttonToSignIn.setOnClickListener {
            startActivity(Intent(this,SignIn::class.java))
            finishAffinity()
        }

    }
}