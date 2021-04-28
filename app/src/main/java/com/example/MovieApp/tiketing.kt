package com.example.MovieApp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_tiketing.*

class tiketing : Fragment() {

    lateinit var ref: DatabaseReference
    lateinit var pref: sharedPref
    lateinit var listenerticketing: ValueEventListener
    private var arrayTicketlist = ArrayList<Checkoutmodel>()
    lateinit var v : View
    var dataCheckout = Checkoutmodel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tiketing, container, false)
        pref = sharedPref(requireContext())
        v = activity?.findViewById(R.id.BottomNav)!!
        v.visibility = View.VISIBLE

        getData()



        return view
    }

    private fun getData() {
        ref = FirebaseDatabase.getInstance().reference.child("User")
            .child(pref.getValue("username").toString()).child("checkout")
        ref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayTicketlist.clear()
                for (getarrayTicket in snapshot.children) {
                    dataCheckout=
                        getarrayTicket.getValue(Checkoutmodel::class.java) as Checkoutmodel
                    arrayTicketlist.add(dataCheckout)
                }
                Log.d("DATAAAA", arrayTicketlist.toString())

                if (arrayTicketlist.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "You haven't bought anything yet",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    val sizemovie = arrayTicketlist.size.toString()
                    totMovie.text = "$sizemovie ticket"
                    rvTicketList.layoutManager = LinearLayoutManager(requireContext())
                    rvTicketList.adapter = AdapterTicketlist(requireContext(), arrayTicketlist) {
                        v.visibility = View.GONE
                        val bundle = Bundle()
                        bundle.putParcelable("DataTicket", it)
                       Navigation.findNavController(requireView()).navigate(R.id.action_tiketing_to_ticketDetail,bundle)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}