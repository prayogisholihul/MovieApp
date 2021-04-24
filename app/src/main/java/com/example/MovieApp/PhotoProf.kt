package com.example.MovieApp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_photo_prof.*
import java.io.File
import java.util.*


class PhotoProf : AppCompatActivity() {

    val permissionImage = 1
    var statusAdd: Boolean = false
    lateinit var filepath: Uri
    lateinit var storageRef: StorageReference
    lateinit var  refFoto:DatabaseReference
    lateinit var pref: sharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_prof)

        refFoto = FirebaseDatabase.getInstance().getReference("User")
        pref = sharedPref(this)
        storageRef = FirebaseStorage.getInstance().reference
        namaProf.text = intent.getStringExtra("nama")

        AddPhoto.setOnClickListener {
            if (statusAdd) {
                btnSave.visibility = View.GONE
            } else {

                ImagePicker.with(this).galleryOnly().compress(1024).start()
            }
        }

        btnSkip.setOnClickListener {
            pref.setValue("AlreadySignUp", "2")
            startActivity(Intent(this@PhotoProf, MainMenu::class.java))
            finishAffinity()
        }

        btnSave.visibility = View.GONE
        btnsave()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                statusAdd = true
                filepath = data?.data!!
                foto.setImageURI(filepath)

                Glide.with(this).load(filepath).apply(RequestOptions.circleCropTransform())
                    .into(foto)

                btnSave.visibility = View.VISIBLE
                //You can get File object from intent
                val file: File = ImagePicker.getFile(data)!!

                //You can also get File Path from intent
                val fileTruePath: String = ImagePicker.getFilePath(data)!!


            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun btnsave() {
        btnSave.setOnClickListener {
            val progressDialog = ProgressDialog(this@PhotoProf)
            progressDialog.setMessage("Loading ....")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val refStorage = storageRef.child("images/" + UUID.randomUUID().toString())
            refStorage.putFile(filepath).addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this@PhotoProf, "Uploaded", Toast.LENGTH_SHORT).show()

                refStorage.downloadUrl.addOnSuccessListener {

                    refFoto.child(intent.getStringExtra("username").toString()).addListenerForSingleValueEvent(object :ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val data = ModelUser()
                            data.url = it.toString()
                            snapshot.ref.child("url").setValue(data.url)

                            pref.setValue("nama", data.Nama)
                            pref.setValue("username", data.Username)
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })


                    pref.setValue("url",it.toString())
                    pref.setValue("AlreadySignUp","2")
                    startActivity(Intent(this@PhotoProf, MainMenu::class.java))
                    finishAffinity()
                }
            }.addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this@PhotoProf, "Failed", Toast.LENGTH_SHORT).show()
            }.addOnProgressListener {
                val progress = 100.0 * it.bytesTransferred / it.totalByteCount
                progressDialog.setMessage("Upload ${progress.toInt()}" + " %")
            }

        }
    }
}