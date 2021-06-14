package com.example.MovieApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_adapter_fand_b_user.view.*

class AdapterFandBUser(private val dataFandB: ArrayList<FandBData>) :
    RecyclerView.Adapter<AdapterFandBUser.ViewHolder>() {


    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FandBData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_adapter_fand_b_user, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nama.text = dataFandB[position].nama
        holder.harga.text = dataFandB[position].harga.toString()


        Glide.with(holder.itemView.context)
            .load(dataFandB[position].picture)
            .error(R.drawable.illustration2)
            .into(holder.picture)

        val status = dataFandB[position].status

        if(!status){
            holder.itemView.isEnabled = false
            holder.harga.text = "Tidak Tersedia"
        }

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(dataFandB[holder.adapterPosition]) }

    }

    override fun getItemCount() = dataFandB.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nama = view.NamaFandB
        val harga = view.HargaFandB
        val picture = view.pictureFandB
    }


}

