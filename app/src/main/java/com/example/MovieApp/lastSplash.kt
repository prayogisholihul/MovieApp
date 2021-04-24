package com.example.MovieApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_last_splash.*

class lastSplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_splash)

        buttonStart.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
            finishAffinity()
        }
    }
}