package com.example.FirebaseWork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.FirebaseWork.databinding.ActivityMainBinding
import com.google.firebase.database.*

class ShowUser : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private  val database  = FirebaseDatabase.getInstance().getReference("Users")
    private  val _result = MutableLiveData<List<User>>()
    val result : LiveData<List<User>>
    get() = _result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}


