package com.example.MovieApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_movie.*

class Detail_movie : AppCompatActivity() {

    private val arrayData = ArrayList<Cast>()
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



            rvCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rvCast.adapter = AdapterFotoCast(this, arrayData)


            ref = FirebaseDatabase.getInstance().reference.child("Film").child(passData.NamaFilm)
                .child("Cast")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
//                    Log.d("asd",snapshot.toString())
                    for (getArrayCast in snapshot.children) {
                        val dataCast: Cast = getArrayCast.getValue(Cast::class.java) as Cast
                        arrayData.add(dataCast)
                        Log.d("DATAAAAAAAA", getArrayCast.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        }
        btnNext.setOnClickListener {
            val i = Intent(this@Detail_movie, Choose_Seat::class.java)
            i.putExtra("PasstoCheckout", passData)
            startActivity(i)
        }
    }
}