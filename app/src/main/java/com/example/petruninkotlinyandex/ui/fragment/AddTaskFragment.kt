package com.example.petruninkotlinyandex.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.ui.viewModel.TaskViewModel
import com.example.petruninkotlinyandex.data.model.TodoItem
import com.example.petruninkotlinyandex.databinding.FragmentAddTaskBinding
import java.util.*

// Фрагмент добавления или редактирования задач
class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Обработчик нажатия на кнопку закрытия
        binding.toolbarButtonClose.setOnClickListener {
            view?.findNavController()?.navigateUp()
        }

        // Обработчик нажатия на кнопку выбора важности задачи
        binding.importanceButton.setOnClickListener {
            showImportanceList(binding.importanceButton)
        }

        // Сохранение изменений задачи
        saveChangesTask(view)

        // Загрузка ранее сохраненной задачи
        pastTask()

        // Проверка наличия текущей задачи и установка соответствующего вида для кнопки удаления
        if (taskViewModel.getCurrentTask() == null) {
            binding.deleteText.setTextColor(ContextCompat.getColor(binding.deleteText.context, R.color.label_primary))
            binding.deleteText.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(
                binding.deleteText.context, R.drawable.delete_gray), null, null, null)
        }
        else {
            // Обработчик нажатия на кнопку удаления задачи
            binding.deleteText.setOnClickListener {
                taskViewModel.getCurrentTask()?.let { itt -> taskViewModel.delete(itt) }
                view?.findNavController()?.navigateUp()
            }
        }

        val months = arrayOf("января", "февраля", "марта","апреля",
            "мая", "июня", "июля", "августа",
            "сентября", "октября", "ноября", "декабря")

        // Обработчик переключения даты
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
                taskViewModel.deleteDate()
            }
        }
    }

    // Сохранение изменений задачи
    private fun saveChangesTask(view: View) {
        // Обработчик нажатия сохранения задачи
        binding.toolbarButtonSave.setOnClickListener {
            // Условие, чтобы не записывать пустую задачу
            if (binding.newTextTask.text.isNotEmpty()) {
                var importance = binding.textImportance.text.toString()

                // Убрать восклицательные знаки у важности
                if(importance == "!!Высокий") importance = importance.drop(2)

                // Проверка на то, что задача создается, а не редактируется
                if(taskViewModel.getCurrentTask() == null){
                    taskViewModel.insert(
                        TodoItem(checkBoxTaskText = binding.newTextTask.text.toString(), importance = importance, currentDate = binding.dateText.text.toString())
                    )
                }
                // Если редактируется, то изменить данные уже существующей задачи
                else{
                    val newTodoItem = taskViewModel.getCurrentTask()
                    if (newTodoItem != null) {
                        newTodoItem.checkBoxTaskText = binding.newTextTask.text.toString()
                        newTodoItem.importance = importance
                        newTodoItem.deadlineDate = binding.dateText.text.toString()
                        taskViewModel.updateTask(newTodoItem)
                    }
                }
                view?.findNavController()?.navigateUp()
            }
            else {
                binding.newTextTask.hint = resources.getString(R.string.edit_text_hint)
            }
        }
    }

    // Загрузка ранее сохраненной задачи
    private fun pastTask() {
        // Подстановка информации о задаче
        val todoItem = taskViewModel.getCurrentTask() ?: return
        var importanceTask = todoItem?.importance
        binding.newTextTask.setText(todoItem?.checkBoxTaskText)

        // Подстановка важности задачи
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

        // Включение переключателя, если есть дата у задачи
        if(todoItem.deadlineDate != ""){
            binding.switchDate.isChecked = true
        }
    }

    // Показать выпадающий список важности задачи
    private fun showImportanceList(view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.importance_menu, popupMenu.menu)

        val highImportance = popupMenu.menu.getItem(2)
        val spannable: SpannableString = SpannableString(highImportance.title.toString())
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.color_red)), 0, spannable.length, 0)
        highImportance.title = spannable

        // Обработчик нажатия на важность
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