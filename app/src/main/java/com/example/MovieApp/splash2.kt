package com.example.MovieApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splash2.*

class splash2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash2)

        buttonNext2.setOnClickListener {
            startActivity(Intent(this,lastSplash::class.java))
            finishAffinity()
        }
    }
}