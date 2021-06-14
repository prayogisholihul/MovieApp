package com.example.MovieApp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.MovieApp.Admin.Admin
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {

    private lateinit var ref: DatabaseReference
    lateinit var pref: sharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        pref = sharedPref(this)

        pref.setValue("noSplash", "1")
        if (pref.getValue("CheckSignIn").equals("1") || pref.getValue("AlreadySignUp").equals("2")) {
            startActivity(Intent(this, MainMenu::class.java))
            finishAffinity()
        }
        ref = FirebaseDatabase.getInstance().getReference("User")
        gotoMainmenu.setOnClickListener {
            when {
                username.text.toString().isEmpty() && pass.text.toString().isEmpty() -> {
                    textInputLayout2.error = "Masukkan Username"
                    textInputLayout3.error = "Masukkan Password"
                }
                username.text.toString().isEmpty() || username.text.toString().contains("@") -> {
                    textInputLayout3.isErrorEnabled = false
                    textInputLayout2.error = "Masukkan Username"
                    textInputLayout2.requestFocus()
                }
                pass.text.toString().isEmpty() -> {
                    textInputLayout2.isErrorEnabled = false
                    textInputLayout3.error = "Masukkan Password"
                    textInputLayout3.requestFocus()
                }
                pass.length() < 6 -> {
                    textInputLayout2.isErrorEnabled = false
                    textInputLayout3.error = "Password min 6 karakter"
                    textInputLayout3.requestFocus()
                }

                username.text.toString() == "admin" && pass.text.toString() == "123456" -> {
                    startActivity(Intent(this, Admin::class.java))
                    finish()
                }
                else -> {
                    signIn(username.text.toString(), pass.text.toString())
                }
            }
        }
        buttonHome.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }

    private fun signIn(username: String, pass: String) {
        val progressDialog = ProgressDialog(this@SignIn)
        progressDialog.setMessage("Loading ....")
        progressDialog.show()
        progressDialog.setCancelable(false)

        ref.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataUser = snapshot.getValue(ModelUser::class.java)
                    when {
                        dataUser == null -> {
                            progressDialog.dismiss()

                            Toast.makeText(this@SignIn, "User Tidak Ditemukan", Toast.LENGTH_SHORT)
                                .show()
                        }
                        pass != dataUser.Pass -> {
                            progressDialog.dismiss()

                            Toast.makeText(this@SignIn, "Password Salah", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            progressDialog.dismiss()

                            pref.setValue("nama", dataUser.Nama)
                            pref.setValue("username", dataUser.Username)
                            pref.setValue("email",dataUser.Email)
                            pref.setValue("pass",dataUser.Pass)
                            pref.setValue("CheckSignIn", "1")

                            startActivity(Intent(this@SignIn, MainMenu::class.java))
                            finishAffinity()
                        }
                    }
                }
            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(this@SignIn,"Salah Input",Toast.LENGTH_SHORT).show()
            }

        })
    }


}

