package com.example.MovieApp

object ObjectFandB {

    private val Nama = arrayOf(
        "Film",
        "Streaming",
        "Film",
        "Streaming"
    )

    private val FandB = arrayOf(
        "Kentang",
        "Cola",
        "Popcorn",
        "Taco"
    )

    private val harga = intArrayOf(
        75000,
        50000,
        45000,
        75000
    )

    val listData: ArrayList<paketData>
        get() {
            val list = arrayListOf<paketData>()
            for (position in Nama.indices) {
                val paket = paketData()
                paket.Nama = Nama[position]
                paket.FandB = FandB[position]
                paket.Harga = harga[position]

                list.add(paket)
            }
            return list
        }

}