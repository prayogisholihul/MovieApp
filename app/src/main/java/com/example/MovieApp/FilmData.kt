package com.example.MovieApp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmData(
    var picture: String = "",
    var NamaFilm: String = "",
    var Genre: String = "",
    var Sinopsis:String = "",
    var Rating: Int = 0
) : Parcelable