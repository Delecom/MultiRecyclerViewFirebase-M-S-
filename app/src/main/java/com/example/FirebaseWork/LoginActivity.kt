package com.example.FirebaseWork

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.FirebaseWork.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var actionBar: ActionBar
    private  lateinit var progressDialog: ProgressDialog
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
      setContentView(binding.root)
        actionBar = supportActionBar!!
        actionBar.title = "Login"
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        binding.linkSign.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        binding.btnLogin.setOnClickListener {
            validateUser()
        }
    }


    private fun validateUser(){
          email = binding.loginEmail.text.toString().trim()
        password = binding.loginPassword.text.toString().trim()
        if (TextUtils.isEmpty(email)){
            binding.loginEmail.requestFocus()
            binding.loginEmail.error = "Please your email"
        }else if (TextUtils.isEmpty(password)){

            binding.loginPassword.requestFocus()
            binding.loginPassword.error = "Please Enter your Password"

        }else {
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser?.email
                Toast.makeText(this,"LoggedIn as $email",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()

            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed due to ",Toast.LENGTH_SHORT).show()

            }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            startActivity(Intent(this,ProfileActivity::class.java))
            finish()

        }
    }
}