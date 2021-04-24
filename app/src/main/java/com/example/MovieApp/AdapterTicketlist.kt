package com.example.MovieApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_adapter_ticketlist.view.*

class AdapterTicketlist(
    private val context: Context,
    private val arrayTicket: ArrayList<Checkoutmodel>,
    val listener: (Checkoutmodel) -> Unit
) : RecyclerView.Adapter<AdapterTicketlist.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_adapter_ticketlist, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.BindItem(arrayTicket[position], listener, context)
    }

    override fun getItemCount() = arrayTicket.size

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val movpict = v.moviePict
        val movname = v.movieName
        val seat = v.seatTicket

        fun BindItem(
            data: Checkoutmodel,
            listener: (Checkoutmodel) -> Unit,
            context: Context
        ) {
            movname.text = data.movieName
            seat.text = data.kursi

            Glide.with(context).load(data.moviePict).error(R.drawable.ic_local_movies_24px)
                .into(movpict)

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }
}