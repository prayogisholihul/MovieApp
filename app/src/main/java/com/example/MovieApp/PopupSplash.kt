package com.example.MovieApp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PopupSplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, splash1::class.java))
        finish()
    }
}