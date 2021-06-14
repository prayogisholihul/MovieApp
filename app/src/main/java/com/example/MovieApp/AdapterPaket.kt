package com.example.MovieApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_adapter_paket.view.*
import java.util.*

class AdapterPaket(private val data:ArrayList<paketData>) : RecyclerView.Adapter<AdapterPaket.Viewholder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: paketData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder  {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_adapter_paket, parent,false)

        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.namakonten.text =data[position].Nama
        holder.fandb.text = data[position].FandB
        holder.harga.text = "Rp.${data[position].Harga.toString()}"

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data[holder.adapterPosition]) }
    }

    override fun getItemCount() = data.size

    class Viewholder(view: View) : RecyclerView.ViewHolder(view) {
        val namakonten = view.namaKonten
        val fandb = view.FandB
        val harga = view.harga
    }
}