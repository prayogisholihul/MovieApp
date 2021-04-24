package com.example.MovieApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_adapter_coming_soon.view.*

class AdapterComingSoon(
    private val context: Context,
    private val dataFilm: ArrayList<FilmData>,
    private val listener: (FilmData) -> Unit
) : RecyclerView.Adapter<AdapterComingSoon.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_adapter_coming_soon, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.BindItem(dataFilm[position],listener,context)
//        val item = dataFilm[position]
//
//        holder.judulFilm.text = item.NamaFilm
//        holder.genreFilm.text = item.Genre
//        holder.ratingFilm.text = item.Rating.toString()
//
//        Glide.with(context).load(item.picture).error(R.drawable.user_pic).circleCrop()
//            .into(holder.pictFilm)
    }

    override fun getItemCount() = dataFilm.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pictFilm = view.pictFilm
        val judulFilm = view.judulFilm
        val genreFilm = view.genreFilm
        val ratingFilm = view.ratingFilm

        fun BindItem(data: FilmData, listener: (FilmData) -> Unit, context: Context) {
            judulFilm.text = data.NamaFilm
            genreFilm.text = data.Genre
            ratingFilm.text = data.Rating.toString()

            Glide.with(context).load(data.picture).error(R.drawable.user_pic).circleCrop()
            .into(pictFilm)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }
}
