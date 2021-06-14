package com.example.MovieApp.Admin.makanandanminuman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.MovieApp.FandBData
import com.example.MovieApp.R
import kotlinx.android.synthetic.main.fragment_adapter_f_and_b.view.*

class AdapterFandB(private val dataFandB: ArrayList<FandBData>) :
    RecyclerView.Adapter<AdapterFandB.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onItemLongClickCallback: OnItemLongClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_adapter_f_and_b, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(dataFandB[position].picture)
            .error(R.drawable.illustration2)
            .into(holder.pictureFandB)

        holder.namaFandB.text = dataFandB[position].nama
        holder.hargaFandB.text = " Rp. ${dataFandB[position].harga.toString()}"

        if(dataFandB[position].status){
          holder.statusFandB.setImageResource(R.drawable.ic_rectangle_21)
        }else{
            holder.statusFandB.setImageResource(R.drawable.ic_rectangle_211)
        }

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(dataFandB[holder.adapterPosition],holder.statusFandB) }

        holder.itemView.setOnLongClickListener { onItemLongClickCallback.longClicked(dataFandB[holder.adapterPosition])
        true}
    }

    override fun getItemCount() = dataFandB.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val namaFandB = view.NamaFandB
        val hargaFandB = view.HargaFandB
        val pictureFandB = view.pictureFandB
        val statusFandB = view.StatusFandB
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FandBData, imgStatus :ImageView)
    }

    fun setOnItemLongClickCallback(onItemLongClickCallback: OnItemLongClickCallback) {
        this.onItemLongClickCallback = onItemLongClickCallback
    }

    interface OnItemLongClickCallback{
        fun longClicked(data: FandBData)
    }

}