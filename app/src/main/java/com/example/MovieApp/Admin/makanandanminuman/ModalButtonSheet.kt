package com.example.MovieApp.Admin.makanandanminuman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.MovieApp.FandBData
import com.example.MovieApp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_modal_button_sheet.view.*

class ModalButtonSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_modal_button_sheet, container, false)

       val data:FandBData = requireArguments().getParcelable<FandBData>("key") as FandBData
        view.setBottomText.text = "Rubah Harga ${data.nama}"

        view.buttonSendHarga.setOnClickListener{
            if(view.setHarga.text.isEmpty()){
                dismiss()
            }else{
                FirebaseDatabase.getInstance().getReference("FoodAndBeverage").child(data.nama).child("harga").setValue(
                    view.setHarga.text.toString().toInt()
                )
            }

        }
        return view
    }
}