package com.example.MovieApp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_pilih__bangku.*
import java.util.*
import kotlin.collections.ArrayList

class Choose_Seat : AppCompatActivity() {

    private lateinit var ref: DatabaseReference
    private lateinit var pref: sharedPref
    private lateinit var getdataMovie: FilmData
    var ticketArray = ArrayList<Checkoutmodel>()
    var total: Int = 0
    var harga: Int = 40000
    lateinit var arrayButton: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih__bangku)

        getdataMovie = intent.getParcelableExtra("PasstoCheckout")!!
//        Log.d("PASSS DATAAA", getdataMovie.NamaFilm)
        pref = sharedPref(this)

        arrayButton = arrayOf(a1, a2, a3, a4, b1, b2, b3, b4, c1, c2, c3, c4, d1, d2, d3, d4)

        getDataBangku()

        for (itemSeat in arrayButton) {
            var booleanSeat = false
            itemSeat.setOnClickListener {
                if (booleanSeat) {
                    itemSeat.setImageResource(R.drawable.ic_rectangle_20)
                    total -= 1
                    booleanSeat = false
                    buyTicket(total)
                } else {
                    itemSeat.setImageResource(R.drawable.ic_rectangle_21)
                    total += 1
                    booleanSeat = true
                    buyTicket(total)
                    val data = Checkoutmodel(
                        itemSeat.resources.getResourceEntryName(itemSeat.id).toUpperCase(
                            Locale.ROOT
                        ), getdataMovie.NamaFilm, getdataMovie.picture, harga
                    )
                    ticketArray.add(data)
                }
            }
        }

        beliTiket.setOnClickListener {
            val i = Intent(this, Checkout::class.java)
            i.putExtra("DataTicket", ticketArray)
            startActivity(i)
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun buyTicket(i: Int) {
        if (i == 0) {
            beliTiket.visibility = View.INVISIBLE
        } else {
            beliTiket.visibility = View.VISIBLE
            beliTiket.text = "Beli Tiket $total"
        }
    }

    fun getDataBangku() {
        ref = FirebaseDatabase.getInstance().reference.child("User")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (getArrayUser in snapshot.children) {
//                    Log.d("DATAAAAAAAAA", getArrayUser.child("checkout").toString())
                    val asd = getArrayUser.child("checkout")
                    for (getCheckout in asd.children) {
                        Log.d("cekot", getCheckout.toString())
                        val data = getCheckout.getValue(Checkoutmodel::class.java)
                        if (data != null) {
                            for (item in arrayButton) {
                                val btnID = item.resources.getResourceEntryName(item.id)
                                    .toUpperCase(Locale.ROOT)
                                if (data.kursi == btnID && data.movieName == getdataMovie.NamaFilm) {
                                    item.setImageResource(R.drawable.ic_rectangle_211)
                                    item.isClickable = false
                                }
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}