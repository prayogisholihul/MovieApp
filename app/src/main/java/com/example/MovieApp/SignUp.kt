package com.example.MovieApp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUp : AppCompatActivity() {

    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        ref = FirebaseDatabase.getInstance().getReference("User")
        btnLanjutkan.setOnClickListener {
            when {
                usernameReg.text.toString().isEmpty() && PassReg.text.toString()
                    .isEmpty() && NamaReg.text.toString().isEmpty() && EmailReg.text.toString()
                    .isEmpty() -> {
                    TILuserReg.error = "Masukkan Username"
                    TILPassReg.error = "Masukkan Password"
                    TILNamaReg.error = "Masukkan Nama"
                    TILEmailReg.error = "Masukkan Email"
                }
                usernameReg.text.toString().isEmpty() -> {
                    TILuserReg.error = "Masukkan Username"
                    TILuserReg.requestFocus()
                }
                PassReg.text.toString().isEmpty() -> {
                    TILPassReg.error = "Masukkan Password"
                    TILPassReg.requestFocus()
                }
                PassReg.length() < 6 -> {
                    TILPassReg.error = "Password min 6 karakter"
                    TILPassReg.requestFocus()
                }
                NamaReg.text.toString().isEmpty() -> {
                    TILNamaReg.error = "Masukkan Nama"
                    TILNamaReg.requestFocus()
                }
                EmailReg.text.toString().isEmpty() || !(EmailReg.text.toString()
                    .contains("@")) -> {
                    TILEmailReg.error = "Masukkan Email"
                    TILEmailReg.requestFocus()
                }
                else -> {
                    Check(
                        usernameReg.text.toString(),
                        PassReg.text.toString(),
                        NamaReg.text.toString(),
                        EmailReg.text.toString()
                    )
                }
            }
        }



        buttonbackarrow.setOnClickListener {
            buttonback()
        }
    }

    private fun Check(username: String, pass: String, nama: String, email: String) {
        val user = ModelUser()
        user.Username = username
        user.Pass = pass
        user.Nama = nama
        user.Email = email

        Register(username, user)
    }

    private fun Register(username: String, data: ModelUser) {
        val progressDialog = ProgressDialog(this@SignUp)
        progressDialog.setMessage("Loading ....")
        progressDialog.show()
        progressDialog.setCancelable(false)

        ref.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!(snapshot.exists())) {
                    progressDialog.dismiss()
                    ref.child(username).setValue(data)
                    val i = Intent(this@SignUp, PhotoProf::class.java)
                    i.putExtra("nama",data.Nama)
                    i.putExtra("username", data.Username)

                    startActivity(i)
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this@SignUp, "User sudah digunakan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUp, "" + error.message, Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun buttonback() {
        startActivity(Intent(this, SignIn::class.java))
        finishAffinity()
    }
}

