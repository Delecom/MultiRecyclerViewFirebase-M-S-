package com.example.FirebaseWork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.MutableMap as MutableMap

class FirebaseStore : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_store)
        val inputFirstName = findViewById<EditText>(R.id.store_first)
        val inputLastName = findViewById<EditText>(R.id.store_last)
        val btnSave = findViewById<Button>(R.id.btn_save)

        btnSave.setOnClickListener {
            val firstname = inputFirstName.text.toString()
            val lastname = inputLastName.text.toString()
            storeFirebase(firstname,lastname)

        }
    }

    private fun storeFirebase(firstname: String, lastname: String) {
        val  db = FirebaseFirestore.getInstance()
        val user : MutableMap<String,Any> = HashMap()
        user["firstname"] = firstname
        user["lastname"] = lastname
        db.collection("/FirebaseWork")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this,"record save successfully",Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                Toast.makeText(this,"record does not  save successfully",Toast.LENGTH_SHORT).show()

            }
//        Firebase Authentication Login SignUp | Android Studio | Kotlin


    }
}