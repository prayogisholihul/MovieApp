package com.example.MovieApp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_streaming.*

class Streaming : AppCompatActivity() {
    var appStreaming: String=""
    lateinit var ref: DatabaseReference
    lateinit var pref: sharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_streaming)

        pref = sharedPref(this)

        netflix.setOnClickListener {
            it.setBackgroundResource(R.color.pink)
            viu.setBackgroundResource(0)
            hbo.setBackgroundResource(0)
            disney.setBackgroundResource(0)

           appStreaming = "netflix"
        }

        viu.setOnClickListener {
            it.setBackgroundResource(R.color.pink)
            netflix.setBackgroundResource(0)
            hbo.setBackgroundResource(0)
            disney.setBackgroundResource(0)

            appStreaming = "viu"
        }
        disney.setOnClickListener {
            it.setBackgroundResource(R.color.pink)
            netflix.setBackgroundResource(0)
            hbo.setBackgroundResource(0)
            viu.setBackgroundResource(0)

            appStreaming = "disney"
        }
        hbo.setOnClickListener {
            it.setBackgroundResource(R.color.pink)
            netflix.setBackgroundResource(0)
            viu.setBackgroundResource(0)
            disney.setBackgroundResource(0)

            appStreaming = "hbo"
        }

        buyStreaming.setOnClickListener {
            if(appStreaming.isEmpty()){
                Toast.makeText(this, "Click salah satu App",Toast.LENGTH_SHORT).show()
            }else {
                FirebaseDatabase.getInstance().getReference("User").child(pref.getValue("username").toString()).child("Streaming")
                    .setValue(appStreaming)

                startActivity(Intent(this,Success_buy::class.java))
                val notifClass = Notification()
                notifClass.notif(this)
            }
        }
    }
}

