package com.example.petruninkotlinyandex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.TaskViewModel
import com.example.petruninkotlinyandex.adapters.TasksAdapter
import com.example.petruninkotlinyandex.databinding.ActivityMainBinding
import com.example.petruninkotlinyandex.repositories.TodoItemsRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}