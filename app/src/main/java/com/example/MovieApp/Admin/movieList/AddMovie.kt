package com.example.MovieApp.Admin.movieList

import android.app.Activity
import android.app.DatePickerDialog
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
import com.example.MovieApp.FilmData
import com.example.MovieApp.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_add_movie.*
import kotlinx.android.synthetic.main.fragment_add_movie.view.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddMovie : Fragment() {

    val calender: Calendar = Calendar.getInstance()
    private lateinit var filePath: Uri
    var FilmData = com.example.MovieApp.FilmData()
    lateinit var ref: DatabaseReference
    private lateinit var storageRef: StorageReference



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storageRef = FirebaseStorage.getInstance().reference

        val date =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calender.set(Calendar.YEAR, year)
                calender.set(Calendar.MONTH, monthOfYear)
                calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd/MM/yy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                val realDate: String = sdf.format(calender.time)
                dateFilm.setText(realDate)
            }
        dateFilm.setOnClickListener {
            DatePickerDialog(
                requireContext(), date, calender
                    .get(Calendar.YEAR), calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }




    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle: Bundle = this.requireArguments()
        val dataTicket = bundle.getParcelable<FilmData>("DataFilmAdmin")

        if (dataTicket != null) {
            namaFilmAdmin.setText(dataTicket.NamaFilm)
            genreFilmAdmin.setText(dataTicket.Genre)
            dateFilm.setText(dataTicket.Date)
            bioskopFilmAdmin.setText(dataTicket.Bioskop)
            ratingFilmAdmin.setText(dataTicket.Rating.toString())
            sinopsisFilmAdmin.setText(dataTicket.Sinopsis)


            Glide.with(this@AddMovie).load(dataTicket.picture)
                .error(R.drawable.user_pic)
                .apply(RequestOptions.circleCropTransform()).into(imgFilmAdmin)

            FilmData.picture = dataTicket.picture

            if( FilmData.picture == dataTicket.picture){
                filePath = Uri.EMPTY
            }
        }


        choosePictAdmin.setOnClickListener {
            pickPict()
        }


        buttonOK.setOnClickListener {

            FilmData.NamaFilm = namaFilmAdmin.text.toString()
            FilmData.Genre = genreFilmAdmin.text.toString()
            FilmData.Date = dateFilm.text.toString()
            FilmData.Bioskop = bioskopFilmAdmin.text.toString()
            FilmData.Rating = ratingFilmAdmin.text.toString().toInt()
            FilmData.Sinopsis = sinopsisFilmAdmin.text.toString()


            ref = FirebaseDatabase.getInstance().reference.child("Film")
            ref.child(FilmData.NamaFilm)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        ref.child(FilmData.NamaFilm).setValue(FilmData)

                        Toast.makeText(requireContext(), "Berhasil", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })




                upPhoto()
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
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
                filePath = data.data!!
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, filePath)
                Glide.with(this@AddMovie).load(bitmap)
                    .error(R.drawable.user_pic)
                    .apply(RequestOptions.circleCropTransform()).into(imgFilmAdmin)
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

        val refStorage = storageRef.child("Film/" + UUID.randomUUID().toString())
        refStorage.putFile(filePath!!).addOnSuccessListener {
            progressDialog.dismiss()
            Toast.makeText(requireContext(), "Uploaded", Toast.LENGTH_SHORT).show()

            refStorage.downloadUrl.addOnSuccessListener {
                ref.child(FilmData.NamaFilm).child("picture")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            FilmData.picture = it.toString()
                            snapshot.ref.setValue(FilmData.picture)

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
}