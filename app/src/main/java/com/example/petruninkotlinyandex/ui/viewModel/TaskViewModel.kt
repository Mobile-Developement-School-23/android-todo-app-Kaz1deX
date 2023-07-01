package com.example.petruninkotlinyandex.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petruninkotlinyandex.data.model.TodoItem
import com.example.petruninkotlinyandex.data.repository.TodoItemsRepository
import com.example.petruninkotlinyandex.locateLazy
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class TaskViewModel: ViewModel() {
    private val todoItemsRepository: TodoItemsRepository by locateLazy()

    // Текущая задача, с которой необходимо работать
    private var currentTask: TodoItem? = null

    // Количество выполненных задач
    private lateinit var completedTasksCount: LiveData<Int>

    // Состояние нажатия кнопки, скрывающей выполненные задачи
    private var eyeIsVisibility = true

    // Состояние показа невыполненных задач
    private val _visibility: MutableStateFlow<Boolean> = MutableStateFlow(true)


    val visibility: StateFlow<Boolean>
        get() = _visibility

    // Переключение состояния "глаза"
    fun invertVisibilityState() {
        _visibility.value = _visibility.value.not()
    }

    // Добавление задачи
    fun insert(todoItem: TodoItem) {
        viewModelScope.launch {
            todoItemsRepository.insert(todoItem)
        }
    }

    // Обновление задачи
    fun updateTask(todoItem: TodoItem) {
        viewModelScope.launch {
            todoItemsRepository.updateTask(todoItem)
        }
    }

    // Получение количества выполненных задач
    fun getCountCompleted(): LiveData<Int> {
        return todoItemsRepository.getCountCompleted()
    }

    // Удалить задачу
    fun delete(todoItem: TodoItem) {
        viewModelScope.launch {
            todoItemsRepository.delete(todoItem)
        }
    }

    // Получить все задачи
    fun getAllTasks(): Flow<List<TodoItem>> = todoItemsRepository.getAllTasks()

    // Получить только невыполненные задачи
    fun getUncompletedTasks(): Flow<List<TodoItem>> = todoItemsRepository.getUncompletedTasks()

    // Установить состояние "глаза"
    fun setEyeVisibility(visibility: Boolean) {
        eyeIsVisibility = visibility
    }

    // Получить состояние "глаза"
    fun getEyeVisibility() = eyeIsVisibility

    // Установить текущую задачу
    fun setCurrentTask(todoItem: TodoItem) {
        currentTask = todoItem
    }

    // Получить текущую задачу
    fun getCurrentTask() = currentTask

    // Очистить текущую задачу
    fun clearCurrentTask() {
        currentTask = null
    }

    // Удаление даты дедлайна
    fun deleteDate() {
        viewModelScope.launch {
            val newTask = currentTask
            if (newTask != null) {
                newTask.deadlineDate = ""
                todoItemsRepository.updateTask(newTask)
            }
        }
    }
}