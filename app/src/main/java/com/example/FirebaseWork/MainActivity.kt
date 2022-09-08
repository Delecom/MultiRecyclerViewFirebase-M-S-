package com.example.FirebaseWork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.FirebaseWork.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
private  lateinit var binding: ActivityMainBinding
private lateinit var database :DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {

            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastName.text.toString()
            val userName = binding.userName.text.toString()
            val email = binding.email.text.toString()
            val phoneNum = binding.signupPhoneNum.text.toString()
            database = FirebaseDatabase.getInstance().getReference("Users")
            val User = User(firstName, lastName,userName, email,phoneNum)
            database.child(userName).setValue(User).addOnSuccessListener{
                binding.firstName.text.clear()
                binding.lastName.text.clear()
                binding.userName.text.clear()
                binding.email.text.clear()
                binding.signupPhoneNum.text.clear()
                Toast.makeText(this,"register success",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"register failed",Toast.LENGTH_SHORT).show()
            }



        }



    }
}