package com.example.petruninkotlinyandex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petruninkotlinyandex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)
    }
}