package com.example.petruninkotlinyandex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petruninkotlinyandex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}