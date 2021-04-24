package com.example.MovieApp

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_dashboard_menu.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.IOException
import java.util.*


class profile : Fragment() {

    lateinit var ref: DatabaseReference
    private lateinit var pref: sharedPref
    private lateinit var storageRef: StorageReference
    private lateinit var  filePath:Uri
    private lateinit var listener:ValueEventListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pref = sharedPref(requireContext())
        storageRef = FirebaseStorage.getInstance().reference
        ref = FirebaseDatabase.getInstance().getReference("User")
            .child(pref.getValue("username").toString())

        buttonDisabled()
        getDataProfile()

        ChoosePhoto.setOnClickListener {
            pickPict()
        }

        buttonEdit.setOnClickListener {
            if (buttonEdit.text.equals("Save")) {
                buttonDisabled()
                buttonEdit.text = "Edit"
                updateProfile(
                    usernameprof.text.toString(),
                    PassProf.text.toString(),
                    NamaProf.text.toString(),
                    EmailProf.text.toString()
                )
            } else if (buttonEdit.text.equals("Edit")) {
                buttonEnabled()
                buttonEdit.text = "Save"
            }
        }
        logout.setOnClickListener {
            val pref = sharedPref(requireContext())
            pref.logout()
            startActivity(Intent(requireContext(), SignIn::class.java))
            activity?.finishAffinity()
            ref.removeEventListener(listener)
        }
    }

    private fun pickPict() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data!!
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, filePath)
                Glide.with(this@profile).load(bitmap)
                    .error(R.drawable.user_pic)
                    .apply(RequestOptions.circleCropTransform()).into(fotoProf)

                upPhoto()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun upPhoto() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Loading ....")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val refStorage = storageRef.child("images/" + UUID.randomUUID().toString())
        refStorage.putFile(filePath).addOnSuccessListener {
            progressDialog.dismiss()
            Toast.makeText(requireContext(), "Uploaded", Toast.LENGTH_SHORT).show()

            refStorage.downloadUrl.addOnSuccessListener {

                ref.child(pref.getValue("usernamae").toString())
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val data = ModelUser()
                            data.url = it.toString()
                            snapshot.ref.child("url").setValue(data.url)
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })

            }
        }.addOnFailureListener {
            progressDialog.dismiss()
            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
        }.addOnProgressListener {
            val progress = 100.0 * it.bytesTransferred / it.totalByteCount
            progressDialog.setMessage("Upload ${progress.toInt()}" + " %")
        }

    }

    private fun getDataProfile(){

        listener = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(ModelUser::class.java)
                if (data != null) {
                    usernameprof.setText(data.Username)
                    PassProf.setText(data.Pass)
                    NamaProf.setText(data.Nama)
                    EmailProf.setText(data.Email)

                    Glide.with(this@profile).load(data.url)
                        .error(R.drawable.user_pic)
                        .apply(RequestOptions.circleCropTransform()).into(fotoProf)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun updateProfile(username: String, pass: String, nama: String, email: String) {
          ref.addListenerForSingleValueEvent(object : ValueEventListener {
              override fun onDataChange(snapshot: DataSnapshot) {
                  ref.child("pass").setValue(pass)
                  ref.child("nama").setValue(nama)
                  ref.child("email").setValue(email)
                  Toast.makeText(requireContext(), "Profil Telah diupdate", Toast.LENGTH_SHORT)
                      .show()
              }

              override fun onCancelled(error: DatabaseError) {
              }

          })
    }
    fun buttonDisabled(){
        usernameprof.isEnabled = false
        NamaProf.isEnabled = false
        EmailProf.isEnabled = false
        PassProf.isEnabled=false
        TILPassProf.isPasswordVisibilityToggleEnabled = false
    }

    fun buttonEnabled(){
//        usernameprof.isEnabled = true
        NamaProf.isEnabled = true
        EmailProf.isEnabled = true
        PassProf.isEnabled =true
        TILPassProf.isPasswordVisibilityToggleEnabled = true
    }
}