package com.example.MovieApp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_ticket_detail.*
import kotlinx.android.synthetic.main.fragment_ticket_detail.view.*
import java.text.SimpleDateFormat
import java.util.*

class ticketDetail : Fragment() {
    lateinit var ref: DatabaseReference


    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ticket_detail, container, false)
        val calendar = Calendar.getInstance().time
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentdate = sdf.format(calendar.time)
        view.DateTicket.text = currentdate
        val bundle: Bundle = this.requireArguments()
        val dataTicket = bundle.getParcelable<Checkoutmodel>("DataTicket")
        Log.d("DATAAAAAA", dataTicket.toString())

        if (dataTicket != null) {
            ref = FirebaseDatabase.getInstance().reference.child("Film").child(dataTicket.movieName)
            view.SeatTicketDetail.text = "Seat no. ${dataTicket.kursi}"
        }
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataFillm: FilmData =
                    snapshot.getValue(FilmData::class.java) as FilmData
                if (dataTicket != null) {
                    NamaFilm.text = dataTicket.movieName
                    GenreTicket.text = dataFillm.Genre
                    RatingTicket.text = dataFillm.Rating.toString()

                    Glide.with(requireView()).load(dataFillm.picture)
                        .error(R.drawable.ic_local_movies_24px)
                        .into(imageTicketFilm)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return view
    }
}
