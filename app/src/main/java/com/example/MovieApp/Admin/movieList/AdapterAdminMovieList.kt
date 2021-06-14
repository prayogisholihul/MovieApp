package com.example.MovieApp.Admin.movieList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.MovieApp.FilmData
import com.example.MovieApp.R
import kotlinx.android.synthetic.main.fragment_adapter_admin_movie_list.view.*
import java.util.*

class AdapterAdminMovieList(private val data: ArrayList<FilmData>) : RecyclerView.Adapter<AdapterAdminMovieList.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FilmData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_adapter_admin_movie_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.judulFilm.text = data[position].NamaFilm
        holder.genreFilm.text = data[position].Genre
        holder.ratingFilm.text = data[position].Rating.toString()


        Glide.with(holder.itemView.context)
            .load(data[position].picture)
            .into(holder.pictFilm)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data[holder.adapterPosition]) }

    }

    override fun getItemCount() = data.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val pictFilm = view.pictFilm2
        val judulFilm = view.judulFilm
        val genreFilm = view.genreFilm
        val ratingFilm = view.ratingFilm
    }
}


