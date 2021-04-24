package com.example.MovieApp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Checkoutmodel (
    var kursi:String = "",
    var movieName:String = "",
    var moviePict:String = "",
    var harga:Int = 0
        ): Parcelable
