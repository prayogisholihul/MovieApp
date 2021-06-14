package com.example.MovieApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_buy_food_and_beverage.view.*

class BuyFoodAndBeverage : Fragment() {
    private val arrayFandB = ArrayList<FandBData>()
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_buy_food_and_beverage, container, false)

        FirebaseDatabase.getInstance().getReference("FoodAndBeverage")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    arrayFandB.clear()
                    for (getData in snapshot.children) {
                        val data: FandBData =
                            getData.getValue(FandBData::class.java) as FandBData

                        arrayFandB.add(data)
                        setAdapter()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


        root.buttongotoPaket.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_buyFoodAndBeverage_to_paketFandB)
        }
        return root
    }

    fun setAdapter() {
        root.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = AdapterFandBUser(arrayFandB)
        root.recyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : AdapterFandBUser.OnItemClickCallback {
            override fun onItemClicked(data: FandBData) {
                Navigation.findNavController(requireView()).navigate(R.id.action_buyFoodAndBeverage_to_success_buy)
            }
        })
    }

}