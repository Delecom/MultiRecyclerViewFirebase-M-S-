package com.example.FirebaseWork.hamza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.FirebaseWork.databinding.ActivitySliderBinding

class Slider : AppCompatActivity() {
    private lateinit var binding: ActivitySliderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySliderBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}



