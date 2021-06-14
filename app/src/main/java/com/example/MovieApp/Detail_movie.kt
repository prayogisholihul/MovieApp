package com.example.MovieApp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_movie.*

class Detail_movie : AppCompatActivity() {

    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val passData = intent.getParcelableExtra<FilmData>("PassData")


        if (passData != null) {
            Glide.with(this).load(passData.picture).centerInside()
                .into(imgFilm)

            judulFilm.text = passData.NamaFilm
            genreFilm.text = passData.Genre
            sinopsisFilm.text = passData.Sinopsis
            ratingFilm.text = passData.Rating.toString()

        }
        btnNext.setOnClickListener {
            val i = Intent(this@Detail_movie, Choose_Seat::class.java)
            i.putExtra("PasstoCheckout", passData)
            startActivity(i)
        }
        streaming.setOnClickListener {
            val i = Intent(this@Detail_movie, Streaming::class.java)
            startActivity(i)
        }
    }
}