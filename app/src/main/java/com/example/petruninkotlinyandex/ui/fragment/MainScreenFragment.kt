package com.example.petruninkotlinyandex.ui.fragment

import com.example.petruninkotlinyandex.ui.gesture.SwipeGesture
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.data.dataSource.network.entities.TodoItemElementResponse
import com.example.petruninkotlinyandex.data.dataSource.network.retrofit.RetrofitSource
import com.example.petruninkotlinyandex.ui.viewModel.TaskViewModel
import com.example.petruninkotlinyandex.ui.adapter.TasksAdapter
import com.example.petruninkotlinyandex.data.model.TodoItem
import com.example.petruninkotlinyandex.databinding.FragmentMainScreenBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Основной фрагмент со списком задач
class MainScreenFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private lateinit var tasksRecyclerView: RecyclerView
//    private val taskViewModel: TaskViewModel by activityViewModels()
    private val taskViewModel: TaskViewModel by activityViewModels()
//    private val tasksAdapter = TasksAdapter(taskViewModel.getAllTasks())
    private val tasksAdapter = TasksAdapter()
    private val binding get() = _binding!!

    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val visibility: StateFlow<Boolean> by lazy {
        taskViewModel.visibility
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)

        taskViewModel.getCountCompleted().observe(viewLifecycleOwner) { count ->
            val counterText = resources.getString(R.string.counter_completed_tasks) + " " + count.toString()
            binding.titleTextCollapsing.text = counterText
        }

        lifecycle.coroutineScope.launch(Dispatchers.IO) {
            visibility.collectLatest { visibilityState ->
                when(visibilityState) {
                    true -> {
                        taskViewModel.getAllTasks().collectLatest { task ->
                            tasksAdapter.submitList(task)
                        }
                    }
                    false -> {
                        taskViewModel.getUncompletedTasks().collectLatest { task ->
                            tasksAdapter.submitList(task)
                        }
                    }
                }
            }
        }

//        lifecycleScope.launch {
//            taskViewModel.getAllTasks().collect { yourList ->
//                tasksAdapter.submitList(yourList)
//            }
//        }

//        val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
//        viewModelScope.launch {
//            taskViewModel.getAllTasks()
//                .collectLatest { artists ->
//                    withContext(Dispatchers.Main) {
//                        tasksAdapter.submitList(artists)
//                    }
//                }
//        }

//        val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
//        viewModelScope.launch {
//            taskViewModel.getAllTasks()
//                .onEach { artists ->
//                    withContext(Dispatchers.Main) {
//                        tasksAdapter.submitList(artists)
//                    }
//                }
//                .collect()
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModelScope.launch {
//            taskViewModel.getAllTasks().collect { artists ->
//                withContext(Dispatchers.Main) {
//                    tasksAdapter.submitList(artists.toMutableList())
//                }
//            }
//        }

//        taskViewModel.getAllTasks().onEach(::renderTasks).launchIn(lifecycleScope)

//        lifecycleScope.launch {
//            taskViewModel.getAllTasks().collect { list ->
//                // Обновите адаптер списка с новым списком 'list'
//                tasksAdapter.updateList(list)
//            }
//        }



//        taskViewModel.getAllTasks()
//            .onEach { taskList ->
//                // Обновить список задач в адаптере
//                tasksAdapter.submitList(taskList)
//            }
//            .launchIn(lifecycleScope)



        tasksRecyclerView =  binding.recyclerTasks
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        // Обновление счетчика выполненных задач
//        binding.titleTextCollapsing.text = "Выполнено - ${taskViewModel.getCounterCompleteTasks()}"
//        binding.titleTextCollapsing.text = "Выполнено - ${taskViewModel.getCountCompleted()}"

        tasksRecyclerView.adapter = tasksAdapter
//        tasksAdapter.tasksList = taskViewModel.getTasks()
//        tasksAdapter.tasksList = taskViewModel.getAllTasks()
        tasksRecyclerView.layoutManager = layoutManager

        // Обработчик нажатия на элемент списка задач
        tasksAdapter.setOnClickListener(object: TasksAdapter.OnItemClickListener {
            override fun onItemClick(todoItem: TodoItem) {
                // Если задача из выполненной стала невыполненной
//                if (!todoItem.isCompleted) taskViewModel.minusCounterCompleteTasks()
//                // Если задача из невыполненной стала выполненной
//                else taskViewModel.plusCounterCompleteTasks()

                // Скрывать выполненную задачу, если кнопка скрытия выполненных задач активна
//                if (!taskViewModel.getEyeVisibility()) taskViewModel.hideCompleteTasks()
                // Обновление счетчика выполненных задач
//                binding.titleTextCollapsing.text = "Выполнено - ${taskViewModel.getCounterCompleteTasks()}"
//                binding.titleTextCollapsing.text = "Выполнено - ${taskViewModel.getCountCompleted()}"
                taskViewModel.updateTask(todoItem)
            }

            override fun onButtonInfoClick(todoItem: TodoItem) {
                taskViewModel.setCurrentTask(todoItem)
                Navigation.findNavController(view).navigate(R.id.action_mainScreenFragment_to_addTaskFragment)
            }
        })

        // Изменение цвета плюса на белый на кнопке добавления
        binding.addButton.setColorFilter(Color.argb(255, 255, 255, 255))

        // Переход во второй фрагмент для добавления новой задачи
        binding.addButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainScreenFragment_to_addTaskFragment)
        }

        // Установка наблюдателя для обновления списка при изменении
//        taskViewModel.getTasks().observe(viewLifecycleOwner, Observer { it?.let {
//            tasksAdapter?.notifyDataSetChanged()
//        } })

        binding.swipeRefreshLayoutMainScreen.setOnRefreshListener {
//            binding.titleTextCollapsing.text = "Выполнено - ${taskViewModel.getCountCompleted()}"
            binding.swipeRefreshLayoutMainScreen.isRefreshing = false
        }



        // Установка корректной иконки при создании фрагмента
        if (taskViewModel.getEyeVisibility()) binding.eyeButton.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.visibility))
        else binding.eyeButton.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.visibility_off))

        // Обработчик нажатия на кнопку скрытия выполненных задач
        binding.eyeButton.setOnClickListener {
            // Если кнопка не была нажата, то скрыть выполненные задачи и изменить иконку
            if (taskViewModel.visibility.value) {
                binding.eyeButton.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.visibility_off))
                taskViewModel.invertVisibilityState()
                taskViewModel.setEyeVisibility(false)
            }
            // Если кнопка была нажата, то показать все задачи и изменить иконку
            else {
                binding.eyeButton.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.visibility))
                taskViewModel.invertVisibilityState()
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
//                            val deleteItem = taskViewModel.getTaskByIndex(position)
                            val deleteItem = tasksAdapter.getItemByPosition(position)
                            if (deleteItem != null) {
                                // Удаление задачи из списка после свайпа
//                                taskViewModel.deleteTaskFromRepository(deleteItem)
                                taskViewModel.delete(deleteItem)
                                // Обновление счетчика выполненных задач
//                                binding.titleTextCollapsing.text = "Выполнено - ${taskViewModel.getCounterCompleteTasks()}"
//                                binding.titleTextCollapsing.text = "Выполнено - ${taskViewModel.getCountCompleted()}"
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

//    private fun renderTasks(tasks: List<TodoItemEntity>) {
//        tasksAdapter.submitList(tasks)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelScope.cancel()
        _binding = null
    }
}