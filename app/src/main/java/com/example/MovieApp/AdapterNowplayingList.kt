package com.example.MovieApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_adapter_now_playing.view.*

class AdapterNowplayingList(
    private val context: Context,
    private val dataFilm: ArrayList<FilmData>,
    private val Listener: (FilmData) -> Unit
) : RecyclerView.Adapter<AdapterNowplayingList.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_adapter_now_playing, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.BindItem(dataFilm[position], Listener, context)
    }

    override fun getItemCount() = dataFilm.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pictFilm = view.pictFilm
        val judulFilm = view.judulFilm
        val genreFilm = view.genreFilm
        val ratingFilm = view.ratingFilm

        fun BindItem(data: FilmData, Listener: (FilmData) -> Unit, context: Context) {
            judulFilm.text = data.NamaFilm
            genreFilm.text = data.Genre
            ratingFilm.text = data.Rating.toString()

            Glide.with(context).load(data.picture).error(R.drawable.user_pic).centerCrop()
                .into(pictFilm)

            itemView.setOnClickListener {
                Listener(data)
            }
        }
    }
}