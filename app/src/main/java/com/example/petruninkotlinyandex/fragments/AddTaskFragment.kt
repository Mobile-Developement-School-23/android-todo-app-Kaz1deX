package com.example.petruninkotlinyandex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.TaskViewModel
import com.example.petruninkotlinyandex.data.TodoItem
import com.example.petruninkotlinyandex.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding
    private val importanceSpinnerArray = arrayOf("Низкий", "Средний", "!! Высокий")
    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        ArrayAdapter<String> importanceSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, importanceSpinner);
        val importanceSpinnerAdapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, importanceSpinnerArray)
        importanceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.importanceSpinner.adapter = importanceSpinnerAdapter

        binding.cancelButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addTaskFragment_to_mainScreenFragment)
        }
        binding.saveButton.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString("newTask", binding.newTextTask.text.toString())
            taskViewModel.addTaskToRepository(TodoItem("0", binding.newTextTask.text.toString(),
                TodoItem.Importance.NORMAL, false, "12-06-2023"))

            Navigation.findNavController(view).navigate(R.id.action_addTaskFragment_to_mainScreenFragment)
        }
    }
}