package com.example.petruninkotlinyandex.fragments

import android.app.DatePickerDialog
import android.graphics.Color
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
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.data.TaskViewModel
import com.example.petruninkotlinyandex.data.TodoItem
import com.example.petruninkotlinyandex.databinding.FragmentAddTaskBinding
import java.lang.reflect.Field
import java.util.*

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

        binding.toolbarButtonClose.setOnClickListener {
//            requireActivity().supportFragmentManager.popBackStack()
//            Navigation.findNavController(view).navigate(R.id.action_addTaskFragment_to_mainScreenFragment)
            view?.findNavController()?.navigateUp()
        }

        binding.importanceButton.setOnClickListener {
            showImportanceList(binding.importanceButton)
        }

        saveChangesTask(view)
        pastTask()

        if (taskViewModel.getCurrentTask() == null) {
            binding.deleteText.setTextColor(ContextCompat.getColor(binding.deleteText.context, R.color.label_primary))
            binding.deleteText.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(
                binding.deleteText.context, R.drawable.delete_gray), null, null, null)
        }
        else {
            binding.deleteText.setOnClickListener {
                taskViewModel.getCurrentTask()?.let { itt -> taskViewModel.deleteTaskFromRepository(itt) }
                view?.findNavController()?.navigateUp()
            }
        }

        val months = arrayOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
        binding.switchDate.setOnClickListener {
            if (binding.switchDate.isChecked) {
                val datePickerDialog = DatePickerDialog(
                    this@AddTaskFragment.requireContext(),
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        val deadlineDate = "$dayOfMonth ${months[monthOfYear]} $year"
                        binding.dateText.text = deadlineDate
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.show()
                datePickerDialog.setOnCancelListener {
                    binding.switchDate.isChecked = false
                }
            }
            else {
                binding.dateText.text = ""
                taskViewModel.deleteData()
            }
        }
    }
    private fun saveChangesTask(view: View) {
        binding.toolbarButtonSave.setOnClickListener {
            if (binding.newTextTask.text.isNotEmpty()) {
                var importance = binding.textImportance.text.toString()
                if(importance == "!!Высокий") importance = importance.drop(2)
                if(taskViewModel.getCurrentTask() == null){
                    taskViewModel.addTaskToRepository(TodoItem(binding.newTextTask.text.toString(), importance, binding.dateText.text.toString()))
                }
                else{
                    taskViewModel.getCurrentTask()?.checkBoxTaskText = binding.newTextTask.text.toString()
                    taskViewModel.getCurrentTask()?.importance = importance
                    taskViewModel.getCurrentTask()?.deadlineDate = binding.dateText.text.toString()
                }
//            requireActivity().supportFragmentManager.popBackStack();
//                Navigation.findNavController(view).navigate(R.id.action_addTaskFragment_to_mainScreenFragment)
                view?.findNavController()?.navigateUp()
            }
            else {
                binding.newTextTask.hint = "Необходимо ввести задачу"
            }
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
                    R.color.color_red
                )
            )
        }
        binding.textImportance.text = importanceTask
        binding.dateText.text = todoItem.deadlineDate

        if(todoItem.deadlineDate != ""){
            binding.switchDate.isChecked = true
        }
    }
    private fun showImportanceList(view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.importance_menu, popupMenu.menu)

        var highImportance = popupMenu.menu.getItem(2)
        var spannable: SpannableString = SpannableString(highImportance.title.toString())
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.color_red)), 0, spannable.length, 0)
        highImportance.title = spannable

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { menuItem: MenuItem? ->
            binding.textImportance.text = menuItem?.title
            if(menuItem!!.itemId == R.id.menu_high)
                binding.textImportance.setTextColor(ContextCompat.getColor(view.context, R.color.color_red))
            else
                binding.textImportance.setTextColor(ContextCompat.getColor(view.context, R.color.label_disable))
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