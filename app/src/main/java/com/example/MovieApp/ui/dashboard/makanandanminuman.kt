package com.example.MovieApp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.MovieApp.AdapterFandB
import com.example.MovieApp.FandBData
import com.example.MovieApp.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_f_b.view.*

class makanandanminuman : Fragment() {

    val arrayFandB = ArrayList<FandBData>()
    lateinit var ref : DatabaseReference
    lateinit var listener: ValueEventListener
    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_f_b, container, false)

        ref = FirebaseDatabase.getInstance().getReference("FoodAndBeverage")
           listener = ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    arrayFandB.clear()
                    for (getData in snapshot.children) {
                        val data: FandBData =
                            getData.getValue(FandBData::class.java) as FandBData

                        arrayFandB.add(data)

                        setAdapter(ref)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        return root
    }

    fun setAdapter(ref: DatabaseReference) {
        root.fandb_rv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = AdapterFandB(arrayFandB)
        root.fandb_rv.adapter = adapter

        adapter.setOnItemClickCallback(object : AdapterFandB.OnItemClickCallback {
            override fun onItemClicked(data: FandBData, imgStatus: ImageView) {
                if (data.status) {
                    ref.child(data.nama).child("status").setValue(false)
                } else {
                    ref.child(data.nama).child("status").setValue(true)
                }


            }
        })

        adapter.setOnItemLongClickCallback(object : AdapterFandB.OnItemLongClickCallback {
            override fun longClicked(data: FandBData) {
                val modalbottomSheetFragment = ModalButtonSheet()
                val bundle = Bundle()
                bundle.putParcelable("key",data)
                modalbottomSheetFragment.arguments = bundle
                modalbottomSheetFragment.show(fragmentManager!!, modalbottomSheetFragment.tag)
            }
        })
    }


    override fun onPause() {
        super.onPause()
        ref.removeEventListener(listener)
    }
}