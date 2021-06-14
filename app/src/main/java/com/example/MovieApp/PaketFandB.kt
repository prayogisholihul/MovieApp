package com.example.MovieApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_paket_fand_b.*

class PaketFandB : Fragment() {
    private var list: ArrayList<paketData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_paket_fand_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.addAll(ObjectFandB.listData)

        rvPaket.layoutManager = LinearLayoutManager(requireContext())
        val adapter = AdapterPaket(list)
        rvPaket.adapter = adapter

        adapter.setOnItemClickCallback(object : AdapterPaket.OnItemClickCallback {
            override fun onItemClicked(data: paketData) {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_paketFandB_to_success_buy)
            }
        })
    }
}
