package com.example.MovieApp.Admin.movieList

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.MovieApp.FilmData
import com.example.MovieApp.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_movie_list.*
import java.util.*

class MovieList : Fragment() {

    private val arrayAdminMovie = ArrayList<FilmData>()
    lateinit var ref: DatabaseReference
    lateinit var listener: ValueEventListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().reference.child("Film")
        listener = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayAdminMovie.clear()
                for (getFilmArray in snapshot.children) {
                    val dataFillm: FilmData =
                        getFilmArray.getValue(FilmData::class.java) as FilmData

                    arrayAdminMovie.add(dataFillm)
                    setAdapter()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        FABaddmovie.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_navigation_home_to_addMovie)
        }


    }

    private fun setAdapter() {
        rvAdminMovie.layoutManager = LinearLayoutManager(requireContext())
        val adapter = AdapterAdminMovieList(arrayAdminMovie)
        rvAdminMovie.adapter = adapter

        adapter.setOnItemClickCallback(object : AdapterAdminMovieList.OnItemClickCallback {
            override fun onItemClicked(data: FilmData) {
                val items =
                    arrayOf<CharSequence>("Edit", "Hapus", "Cancel")
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setTitle("Option")
                builder.setItems(items, DialogInterface.OnClickListener { dialog, item ->
                    when (item) {
                        0 -> {
                            val bundle = Bundle()
                            bundle.putParcelable("DataFilmAdmin", data)
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_navigation_home_to_addMovie,bundle)
                        }
                        1 -> {
                            ref.child(data.NamaFilm).removeValue()
                        }
                        2 -> {
                        }
                    }
                })
                val alert: AlertDialog = builder.create()
                alert.show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ref.removeEventListener(listener)

    }

}