package com.example.MovieApp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dashboard_menu.*
import kotlinx.android.synthetic.main.fragment_dashboard_menu.view.*
import java.util.*
import kotlin.collections.ArrayList

class Dashboard_menu : Fragment() {

    lateinit var pref: sharedPref
    lateinit var ref: DatabaseReference
    val arrayFilmNowplaying = ArrayList<FilmData>()
    val arrayFilmComingsoon = ArrayList<FilmData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pref = sharedPref(requireContext())
        ref = FirebaseDatabase.getInstance().getReference("Film")

        getDataFilm()

        val refData: DatabaseReference = FirebaseDatabase.getInstance().getReference("User")
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Loading ....")
        progressDialog.show()
        progressDialog.setCancelable(false)

        refData.child(pref.getValue("username").toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(snapshot: DataSnapshot) {
                    val database = snapshot.getValue(ModelUser::class.java)

                    if (database != null) {
                        tvSaldo.text = "Rp.${database.Saldo}"
                        tvNamaUser.text = database.Nama
                    }

                    if (pref.getValue("url").isNullOrEmpty()) {
                        if (database != null) {
                            Glide.with(this@Dashboard_menu).load(database.url)
                                .error(R.drawable.user_pic)
                                .apply(RequestOptions.circleCropTransform()).into(fotoProp)

                        }
                    } else {
                        Glide.with(this@Dashboard_menu).load(pref.getValue("url"))
                            .error(R.drawable.user_pic)
                            .apply(RequestOptions.circleCropTransform()).into(fotoProp)
                    }
                    progressDialog.dismiss()
                }

                override fun onCancelled(error: DatabaseError) {
                    progressDialog.dismiss()
                    Toast.makeText(
                        requireContext(),
                        "can't get Data",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
            )

        rvNowPlaying.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvNowPlaying.adapter = AdapterNowplayingList(requireContext(), arrayFilmNowplaying){}

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        rvComingSoon.layoutManager = layoutManager
    }


    fun getDataFilm(){
      ref.addValueEventListener(object : ValueEventListener {
          override fun onDataChange(snapshot: DataSnapshot) {
                arrayFilmNowplaying.clear()
                arrayFilmComingsoon.clear()
              for (getFilmArray in snapshot.children) {
                  val dataFillm: FilmData =
                      getFilmArray.getValue(FilmData::class.java) as FilmData
                  arrayFilmComingsoon.add(dataFillm)
                  arrayFilmNowplaying.add(dataFillm)
              }
              rvComingSoon.adapter = AdapterComingSoon(requireContext(), arrayFilmComingsoon){
                  val i = Intent(requireContext(), Detail_movie::class.java)
                  i.putExtra("PassData", it)
                  activity?.startActivity(i)
              }
          }
          override fun onCancelled(error: DatabaseError) {
              TODO("Not yet implemented")
          }

      })
    }
}