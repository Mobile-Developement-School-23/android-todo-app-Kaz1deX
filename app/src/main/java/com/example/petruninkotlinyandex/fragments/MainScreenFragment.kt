package com.example.petruninkotlinyandex.fragments

import com.example.petruninkotlinyandex.gesture.SwipeGesture
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.data.TaskViewModel
import com.example.petruninkotlinyandex.adapters.TasksAdapter
import com.example.petruninkotlinyandex.data.TodoItem
import com.example.petruninkotlinyandex.databinding.FragmentMainScreenBinding

// Основной фрагмент со списком задач
class MainScreenFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private lateinit var tasksRecyclerView: RecyclerView
    private val tasksAdapter = TasksAdapter()
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
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        // Обновление счетчика выполненных задач
        binding.titleTextCollapsing.text = "Выполнено - ${taskViewModel.getCounterCompleteTasks()}"

        // Обработчик нажатия на элемент списка задач
        tasksAdapter.setOnClickListener(object: TasksAdapter.OnItemClickListener {
            override fun onItemClick(todoItem: TodoItem) {
                // Если задача из выполненной стала невыполненной
                if (!todoItem.isCompleted) taskViewModel.minusCounterCompleteTasks()
                // Если задача из невыполненной стала выполненной
                else taskViewModel.plusCounterCompleteTasks()

                // Скрывать выполненную задачу, если кнопка скрытия выполненных задач активна
                if (!taskViewModel.getEyeVisibility()) taskViewModel.hideCompleteTasks()
                // Обновление счетчика выполненных задач
                binding.titleTextCollapsing.text = "Выполнено - ${taskViewModel.getCounterCompleteTasks()}"
            }
        })

        tasksRecyclerView.adapter = tasksAdapter
        tasksAdapter.tasksList = taskViewModel.getTasks()
        tasksRecyclerView.layoutManager = layoutManager

        // Изменение цвета плюса на белый на кнопке добавления
        binding.addButton.setColorFilter(Color.argb(255, 255, 255, 255))

        // Переход во второй фрагмент для добавления новой задачи
        binding.addButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainScreenFragment_to_addTaskFragment)
        }

        // Установка наблюдателя для обновления списка при изменении
        taskViewModel.getTasks().observe(viewLifecycleOwner, Observer { it?.let {
            tasksAdapter?.notifyDataSetChanged()
        } })

        binding.swipeRefreshLayoutMainScreen.setOnRefreshListener {
            binding.swipeRefreshLayoutMainScreen.isRefreshing = false
        }

        // Установка корректной иконки при создании фрагмента
        if (taskViewModel.getEyeVisibility()) binding.eyeButton.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.visibility))
        else binding.eyeButton.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.visibility_off))

        // Обработчик нажатия на кнопку скрытия выполненных задач
        binding.eyeButton.setOnClickListener {
            // Если кнопка не была нажата, то скрыть выполненные задачи и изменить иконку
            if (taskViewModel.getEyeVisibility()) {
                binding.eyeButton.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.visibility_off))
                taskViewModel.hideCompleteTasks()
                taskViewModel.setEyeVisibility(false)
            }
            // Если кнопка была нажата, то показать все задачи и изменить иконку
            else {
                binding.eyeButton.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.visibility))
                taskViewModel.showAllTasks()
                taskViewModel.setEyeVisibility(true)
            }
        }

        swipeToGesture(tasksRecyclerView)
    }

    // Метод, осуществляющий свайп задачи влево для удаления
    private fun swipeToGesture(itemRecyclerView: RecyclerView?){
        val swipeGesture = object : SwipeGesture(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                try{
                    when(direction){
                        ItemTouchHelper.LEFT->{
                            val deleteItem = taskViewModel.getTaskByIndex(position)
                            if (deleteItem != null) {
                                // Удаление задачи из списка после свайпа
                                taskViewModel.deleteTaskFromRepository(deleteItem)
                                // Обновление счетчика выполненных задач
                                binding.titleTextCollapsing.text = "Выполнено - ${taskViewModel.getCounterCompleteTasks()}"
                            }
                        }
                    }
                }
                catch (e: Exception){
                    Toast.makeText(this@MainScreenFragment.requireContext(), e.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        // Обработка жестов свайпа
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(itemRecyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}