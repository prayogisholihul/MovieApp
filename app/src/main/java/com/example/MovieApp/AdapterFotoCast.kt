package com.example.MovieApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_adapter_foto_cast.view.*

class AdapterFotoCast(private val context: Context, private val data: ArrayList<Cast>) :
    RecyclerView.Adapter<AdapterFotoCast.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_adapter_foto_cast, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.BindItem(data[position], context)
    }

    override fun getItemCount() = data.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fotocast = view.FotoCast
        val namacast = view.NamaCast

        fun BindItem(data: Cast, context: Context) {
            namacast.text=data.NamaCast

            Glide.with(context).load(data.fotoCast).circleCrop()
                .into(fotocast)
        }
    }
}
