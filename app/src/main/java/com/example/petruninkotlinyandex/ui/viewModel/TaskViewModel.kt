package com.example.petruninkotlinyandex.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petruninkotlinyandex.data.dataSource.room.TodoItemEntity
import com.example.petruninkotlinyandex.data.model.TodoItem
import com.example.petruninkotlinyandex.data.repository.TodoItemsRepository
import com.example.petruninkotlinyandex.locateLazy
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class TaskViewModel: ViewModel() {
    private val todoItemsRepository: TodoItemsRepository by locateLazy()
    private var currentTask: TodoItem? = null
    private lateinit var completedTasksCount: LiveData<Int>
    // Текущая задача, с которой необходимо работать

    private val _visibility: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val visibility: StateFlow<Boolean>
        get() = _visibility

    fun invertVisibilityState() {
        _visibility.value = _visibility.value.not()
    }
//    private val uncompletedTasks = todoItemsRepository.getUncompletedTasks().asLiveDataFlow()

//    private fun <T> Flow<T>.asLiveDataFlow() = shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    // Добавление задачи в репозиторий
    fun insert(todoItem: TodoItem) {
        viewModelScope.launch {
            todoItemsRepository.insert(todoItem)
        }
    }

    fun updateTask(todoItem: TodoItem) {
        viewModelScope.launch {
            todoItemsRepository.updateTask(todoItem)
        }
    }

    fun getCountCompleted(): LiveData<Int> {
        return todoItemsRepository.getCountCompleted()
    }

//    fun getCountCompleted(): LiveData<Int> {
//        getCountCompletedThis()
//        return completedTasksCount
//    }
//
//    private fun getCountCompletedThis() {
//        viewModelScope.launch {
//            completedTasksCount = todoItemsRepository.getCountCompleted()
//        }
//    }

    fun delete(todoItem: TodoItem) {
        viewModelScope.launch {
            todoItemsRepository.delete(todoItem)
        }
    }

    fun getAllTasks(): Flow<List<TodoItem>> = todoItemsRepository.getAllTasks()
    fun getUncompletedTasks(): Flow<List<TodoItem>> = todoItemsRepository.getUncompletedTasks()

//    fun getUncompletedTasks() = uncompletedTasks

//    fun plusCounterCompleteTasks() {
//        todoItemsRepository.plusCounterCompleteTasks()
//    }
//
//    fun minusCounterCompleteTasks() {
//        todoItemsRepository.minusCounterCompleteTasks()
//    }
//
//    fun getCounterCompleteTasks() = todoItemsRepository.getCounterCompleteTasks()

    fun setEyeVisibility(visibility: Boolean) {
        todoItemsRepository.setEyeVisibility(visibility)
    }

    fun getEyeVisibility() = todoItemsRepository.getEyeVisibility()

    fun setCurrentTask(todoItem: TodoItem) {
        currentTask = todoItem
    }

//    fun getTaskById(idTask: Int): TodoItem {
//        viewModelScope.launch {
//            currentTask = todoItemsRepository.getTaskById(idTask)
//        }
//    }

    fun getCurrentTask() = currentTask

    fun clearCurrentTask() {
        currentTask = null
    }

    // Удаление даты дедлайна
    fun deleteDate() {
        viewModelScope.launch {
//            val newTask = todoItemsRepository.getTaskById(currentIdTask)
            val newTask = currentTask
            if (newTask != null) {
                newTask.deadlineDate = ""
                todoItemsRepository.updateTask(newTask)
            }
        }
    }

//    fun hideCompleteTasks() {
//        tasks = todoItemsRepository.getUncompletedTasks().asLiveDataFlow()
//    }
//
//    fun showAllTasks() {
//        tasks = todoItemsRepository.getAllTasks().asLiveDataFlow()
//    }
}