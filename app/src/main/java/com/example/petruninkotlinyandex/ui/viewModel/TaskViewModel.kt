package com.example.petruninkotlinyandex.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petruninkotlinyandex.data.dataBase.TodoItemEntity
import com.example.petruninkotlinyandex.data.repository.TodoItemsRepository
import com.example.petruninkotlinyandex.locateLazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

open class TaskViewModel: ViewModel() {
    private val todoItemsRepository: TodoItemsRepository by locateLazy()
    private var tasks = todoItemsRepository.getAllTasks().asLiveDataFlow()
    private lateinit var completedTasksCount: LiveData<Int>
//    private val uncompletedTasks = todoItemsRepository.getUncompletedTasks().asLiveDataFlow()

    private fun <T> Flow<T>.asLiveDataFlow() = shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    // Добавление задачи в репозиторий
    fun insert(todoItem: TodoItemEntity) {
        viewModelScope.launch {
            todoItemsRepository.insert(todoItem)
        }
    }

    fun updateTask(todoItem: TodoItemEntity) {
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

    fun delete(todoItem: TodoItemEntity) {
        viewModelScope.launch {
            todoItemsRepository.delete(todoItem)
        }
    }

    fun getAllTasks() = tasks

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

    fun setCurrentTask(todoItem: TodoItemEntity) {
        todoItemsRepository.setCurrentTask(todoItem)
    }

    fun getCurrentTask() = todoItemsRepository.getCurrentTask()

    fun clearCurrentTask() {
        todoItemsRepository.clearCurrentTask()
    }

    fun deleteDate() {
        todoItemsRepository.deleteDate()
    }

    fun hideCompleteTasks() {
        tasks = todoItemsRepository.getUncompletedTasks().asLiveDataFlow()
    }

    fun showAllTasks() {
        tasks = todoItemsRepository.getAllTasks().asLiveDataFlow()
    }
}