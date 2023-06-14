package com.example.petruninkotlinyandex.fragments

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.data.TaskViewModel
import com.example.petruninkotlinyandex.data.TodoItem
import com.example.petruninkotlinyandex.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val positionTask = arguments?.getInt("currentModel") ?: -1
        if (positionTask != -1) taskViewModel.setCurrentTask(taskViewModel.getTaskById(positionTask))

        binding.cancelButton.setOnClickListener {
//            requireActivity().supportFragmentManager.popBackStack()
            Navigation.findNavController(view).navigate(R.id.action_addTaskFragment_to_mainScreenFragment)
        }

        binding.importanceButton.setOnClickListener {
            showImportanceList(binding.importanceButton)
        }

        saveChangesTask(view)
        pastTask()

//        binding.deleteText.setOnClickListener {
//            if (positionTask != -1) {
//                taskViewModel.deleteTaskFromRepository(positionTask)
//                Navigation.findNavController(view).navigate(R.id.action_addTaskFragment_to_mainScreenFragment)
//            }
//        }
    }
    private fun saveChangesTask(view: View) {
        binding.saveText.setOnClickListener {
            var importance = binding.textImportance.text.toString()
            //Toast.makeText(requireActivity(), "Importance: $importance", Toast.LENGTH_SHORT).show()
            if(importance == "!!Высокий") importance = importance.drop(2)
            if(taskViewModel.getCurrentTask() == null){
                taskViewModel.addTaskToRepository(TodoItem(binding.newTextTask.text.toString(), importance))
                val test = taskViewModel.getTaskById(6).checkBoxTaskText
                Toast.makeText(requireActivity(), "$test", Toast.LENGTH_SHORT).show()
            }
            else{
                taskViewModel.getCurrentTask()?.checkBoxTaskText = binding.newTextTask.text.toString()
                taskViewModel.getCurrentTask()?.importance = importance
            }
//            requireActivity().supportFragmentManager.popBackStack();
            Navigation.findNavController(view).navigate(R.id.action_addTaskFragment_to_mainScreenFragment)
        }
    }
    private fun pastTask() {
        val todoItem = taskViewModel.getCurrentTask() ?: return
        var importanceTask = todoItem?.importance
        binding.newTextTask.setText(todoItem?.checkBoxTaskText)
        if(importanceTask.equals("Высокий")) {
            importanceTask = "!!$importanceTask"
            binding.textImportance.setTextColor(
                ContextCompat.getColor(
                    binding.importanceButton.context,
                    R.color.red_high_importance
                )
            )
        }
        binding.textImportance.text = importanceTask
    }
    private fun showImportanceList(view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.importance_menu, popupMenu.menu)

        var highImportance = popupMenu.menu.getItem(2)
        var spannable: SpannableString = SpannableString(highImportance.title.toString())
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.red_high_importance)), 0, spannable.length, 0)
        highImportance.title = spannable

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { menuItem: MenuItem? ->
            binding.textImportance.text = menuItem?.title
            if(menuItem!!.itemId == R.id.menu_high)
                binding.textImportance.setTextColor(ContextCompat.getColor(view.context, R.color.red_high_importance))
            else
                binding.textImportance.setTextColor(ContextCompat.getColor(view.context, R.color.gray_text))
            true
        })
        popupMenu.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        taskViewModel.clearCurrentTask()
        _binding = null
    }
}