package com.example.FirebaseWork

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.FirebaseWork.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
     private lateinit var actionBar: ActionBar
     private lateinit var binding: ActivitySignUpBinding
     private lateinit var progressDialog: ProgressDialog
     private lateinit var firebaseAuth: FirebaseAuth
      private var email = ""
      private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar = supportActionBar!!
        actionBar.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("creating account In...")
        progressDialog.setCanceledOnTouchOutside(false)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        email = binding.signUpMail.text.toString().trim()
        password = binding.signUpPass.text.toString().trim()
       if (TextUtils.isEmpty(password)){
            binding.signUpPass.error = "please Enter your password"
        }else{
            firebaseSignup()
        }

    }

    private fun firebaseSignup() {
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email  = firebaseUser?.email
                Toast.makeText(this,"Account created with email $email",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this ,LoginActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Signup failed ",Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}