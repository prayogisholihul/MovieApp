package com.example.MovieApp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_adapter_rvticket.view.*

class AdapterTicket(private val data: ArrayList<Checkoutmodel>, var context: Context) :
    RecyclerView.Adapter<AdapterTicket.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_adapter_rvticket, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], context)
    }

    override fun getItemCount() = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val seat = view.seat
        val harga = view.hargaSeat

        @SuppressLint("SetTextI18n")
        fun bindItem(data: Checkoutmodel, context: Context) {
            seat.text = "Seat No.${data.kursi}"
            harga.text = "Rp. ${data.harga}"
        }
    }
}
