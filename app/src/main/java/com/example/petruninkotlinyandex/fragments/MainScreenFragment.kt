package com.example.petruninkotlinyandex.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.data.TaskViewModel
import com.example.petruninkotlinyandex.adapters.TasksAdapter
import com.example.petruninkotlinyandex.data.TodoItem
import com.example.petruninkotlinyandex.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private lateinit var tasksRecyclerView: RecyclerView
    private val taskViewModel: TaskViewModel by activityViewModels()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasksRecyclerView =  binding.recyclerTasks

        val tasksAdapter = TasksAdapter()
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        tasksRecyclerView.adapter = tasksAdapter
        tasksAdapter.tasksList = taskViewModel.getTasks().value ?: emptyList()

        tasksRecyclerView.layoutManager = layoutManager

        binding.addButton.setColorFilter(Color.argb(255, 255, 255, 255));
        binding.addButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainScreenFragment_to_addTaskFragment)
        }

        taskViewModel.getTasks().observe(viewLifecycleOwner, Observer { it?.let {
            tasksRecyclerView.adapter?.notifyDataSetChanged()
//            Toast.makeText(context, "UPDATING RECYCLER_VIEW ADAPTER", Toast.LENGTH_SHORT).show()
        } })

        binding.swipeRefreshLayoutMainScreen.setOnRefreshListener {
            taskViewModel.addTaskToRepository(TodoItem("My new Text", "Высокий"))
            tasksAdapter.notifyDataSetChanged()
            binding.swipeRefreshLayoutMainScreen.isRefreshing = false
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}