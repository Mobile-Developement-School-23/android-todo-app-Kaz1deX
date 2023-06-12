package com.example.petruninkotlinyandex.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.TaskViewModel
import com.example.petruninkotlinyandex.adapters.TasksAdapter
import com.example.petruninkotlinyandex.data.TodoItem
import com.example.petruninkotlinyandex.databinding.FragmentMainScreenBinding
import com.example.petruninkotlinyandex.repositories.TodoItemsRepository

class MainScreenFragment : Fragment() {
    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var tasksRecyclerView: RecyclerView
    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)

//        val args = this.arguments
//        val inputTask = arguments?.getString("newTask") ?: "None"
//        todoItemsRepository.addTask(TodoItem("0", inputTask, TodoItem.Importance.NORMAL, false, "12-06-2023"))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasksRecyclerView =  binding.recyclerTasks

        val tasksAdapter = TasksAdapter()
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        tasksRecyclerView.adapter = tasksAdapter
        tasksRecyclerView.layoutManager = layoutManager

//        tasksAdapter.tasksList = todoItemsRepository.getTasks(requireActivity())
        tasksAdapter.tasksList = taskViewModel.getTasks(requireActivity())
//        tasksAdapter.notifyDataSetChanged()

        binding.addButton.setColorFilter(Color.argb(255, 255, 255, 255));
        binding.addButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainScreenFragment_to_addTaskFragment)
        }
    }
}