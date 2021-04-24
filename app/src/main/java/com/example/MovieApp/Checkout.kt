package com.example.MovieApp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_checkout.*

class Checkout : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var pref: sharedPref
    private var totalharga = 0
    lateinit var progressDialog: ProgressDialog
    private lateinit var listenerBuy: ValueEventListener
    lateinit var dataCheckout :ArrayList<Checkoutmodel>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        bayarButton.visibility = View.INVISIBLE

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading ....")
        progressDialog.setCancelable(false)

        pref = sharedPref(this)
        getSaldo()
        dataCheckout = intent.getSerializableExtra("DataTicket") as ArrayList<Checkoutmodel>

        for (i in dataCheckout.indices) {
            totalharga += dataCheckout[i].harga
        }

        tvTotalHarga.text = "Total harga Rp. ${totalharga}"
        Log.d("PASSS DATAAAA", dataCheckout.toString())

        rvTicket.layoutManager = LinearLayoutManager(this)
        rvTicket.adapter = AdapterTicket(dataCheckout, this)

        buttonbackToHome.setOnClickListener {
            finish()
        }
    }

    private fun getSaldo() {
        ref = FirebaseDatabase.getInstance().reference.child("User")
            .child(pref.getValue("username").toString())
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bayarButton.visibility = View.VISIBLE
                val dataSnapshot = snapshot.getValue(ModelUser::class.java)
                if (dataSnapshot != null) {
                    tvSaldo.text = "Rp.${dataSnapshot.Saldo.toString()}"
                    prosesSaldo(dataSnapshot)
                }
                progressDialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun prosesSaldo(dataUser: ModelUser) {

        val animSaldo = AnimationUtils.loadAnimation(this, R.anim.shake_anim)
        animSaldo.repeatCount = 5
        bayarButton.setOnClickListener {
            if (totalharga > dataUser.Saldo) {
                errorSaldo.startAnimation(animSaldo)
                errorSaldo.visibility = View.VISIBLE
                progressDialog.dismiss()
            } else if (totalharga <= dataUser.Saldo) {
                progressDialog.show()
                val prosesBuy = dataUser.Saldo - totalharga
                ref.child("checkout").setValue(dataCheckout)
                ref.child("saldo").setValue(prosesBuy)
                startActivity(Intent(this, Success_buy::class.java))
                finish()
            }
        }

    }
}
