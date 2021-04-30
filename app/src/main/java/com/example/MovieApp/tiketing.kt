package com.example.MovieApp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_tiketing.view.*

class tiketing : Fragment() {

    lateinit var ref: DatabaseReference
    lateinit var pref: sharedPref
    lateinit var listenerticketing: ValueEventListener
    private var arrayTicketlist = ArrayList<Checkoutmodel>()
    lateinit var viewBottomNav: View
    lateinit var viewIconTicket: View
    lateinit var viewIconProfile: View
    lateinit var viewIconMainMenu: View
    lateinit var piu: View
    var dataCheckout = Checkoutmodel()
    val mainMenu = MainMenu()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        piu = inflater.inflate(R.layout.fragment_tiketing, container, false)
        pref = sharedPref(requireContext())
        ref = FirebaseDatabase.getInstance().reference.child("User")
            .child(pref.getValue("username").toString()).child("checkout")
        viewBottomNav = activity?.findViewById(R.id.BottomNav)!!
        viewBottomNav.visibility = View.VISIBLE
        viewIconTicket = activity?.findViewById(R.id.menu_tiketing)!!
        viewIconMainMenu = activity?.findViewById(R.id.main_menu)!!
        viewIconProfile = activity?.findViewById(R.id.menu_profile)!!


        mainMenu.changeIcon(viewIconMainMenu as ImageView, R.drawable.ic_home)
        mainMenu.changeIcon(viewIconTicket as ImageView, R.drawable.ic_tiket_active)
        mainMenu.changeIcon(viewIconProfile as ImageView, R.drawable.ic_profile)

        getData()



            return piu
    }

    fun getData() {
        listenerticketing = ref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayTicketlist.clear()
                for (getarrayTicket in snapshot.children) {
                    dataCheckout =
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
                    piu.totMovie.text = "$sizemovie ticket"
                    piu.rvTicketList.layoutManager = LinearLayoutManager(requireContext())
                    piu.rvTicketList.adapter =
                        AdapterTicketlist(requireContext(), arrayTicketlist) {
                            viewBottomNav.visibility = View.GONE
                            val bundle = Bundle()
                            bundle.putParcelable("DataTicket", it)
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_tiketing_to_ticketDetail, bundle)
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onPause() {
        super.onPause()
        ref.removeEventListener(listenerticketing)
    }
}