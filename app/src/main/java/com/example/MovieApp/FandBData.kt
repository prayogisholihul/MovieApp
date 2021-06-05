package com.example.MovieApp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FandBData(
    var status:Boolean = false,
    var nama:String = "",
    var picture:String = "",
    var harga :Int =0
) : Parcelable
