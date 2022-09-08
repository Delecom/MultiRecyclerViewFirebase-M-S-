package com.example.FirebaseWork

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*
import kotlin.collections.HashMap


class GalleryActivity : AppCompatActivity() {

        private val PICK_IMAGE_REQUEST = 71
        private var filePath: Uri? = null
        private var firebaseStore: FirebaseStorage? = null
        private var storageReference: StorageReference? = null


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            /*setContentView(R.layout.activity_gallery)
            setSupportActionBar(toolbar)*/
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            firebaseStore = FirebaseStorage.getInstance()
            storageReference = FirebaseStorage.getInstance().reference
/*
            btn_choose_image.setOnClickListener { launchGallery() }
            btn_upload_image.setOnClickListener { uploadImage() }*/
        }

        private fun launchGallery() {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
                if(data == null || data.data == null){
                    return
                }

                filePath = data.data
              /*  try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                    uploadImage().setImageBitmap(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                }*/
            }
        }

        private fun addUploadRecordToDb(uri: String){
            val db = FirebaseFirestore.getInstance()

            val data = HashMap<String, Any>()
            data["imageUrl"] = uri

            db.collection("posts")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Saved to DB", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error saving to DB", Toast.LENGTH_LONG).show()
                }
        }

        private fun uploadImage(){
            if(filePath != null){
                val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
                val uploadTask = ref?.putFile(filePath!!)

                val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        addUploadRecordToDb(downloadUri.toString())
                    } else {

                    }
                }?.addOnFailureListener{

                }
            }else{
                Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
            }
        }

    }



