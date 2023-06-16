package com.example.petruninkotlinyandex.data

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petruninkotlinyandex.data.TodoItem
import com.example.petruninkotlinyandex.repositories.TodoItemsRepository
import java.util.*

open class TaskViewModel: ViewModel() {
//    val textTask: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val todoItemsRepository = TodoItemsRepository()
    private var tasks: MutableLiveData<List<TodoItem>> = todoItemsRepository.getTasks()
    private var currentTask: TodoItem? = null
    private var eyeIsVisibility: Boolean = true
    private var counterCompleteTasks: Int = 0
    fun getTasks(): MutableLiveData<List<TodoItem>> {
        return tasks
    }
    fun addTaskToRepository(todoItem: TodoItem) {
        todoItemsRepository.addTaskToRepository(todoItem)
    }
    fun deleteTaskFromRepository(todoItem: TodoItem) {
        todoItemsRepository.deleteTaskFromRepository(todoItem)
        if (todoItem.isCompleted) {
            minusCounterCompleteTasks()
        }
    }
    fun setCurrentTask(todoItem: TodoItem) {
        currentTask = todoItem
    }
    fun clearCurrentTask() {
        currentTask = null
    }
    fun getTaskById(position: Int): TodoItem {
        return todoItemsRepository.getTaskById(position)
    }
    fun getCurrentTask() = currentTask

    fun hideCompleteTasks() {
        tasks.value = todoItemsRepository.getTasks().value?.filter { !it.isCompleted}
    }

    fun showAllTasks() {
        tasks.value = todoItemsRepository.getAllTasks().value
    }

    fun getEyeVisibility(): Boolean {
        return eyeIsVisibility
    }

    fun setEyeVisibility(visibility: Boolean) {
        eyeIsVisibility = visibility
    }

    fun getCounterCompleteTasks(): Int {
        return counterCompleteTasks
    }

    fun plusCounterCompleteTasks() {
        counterCompleteTasks++
    }

    fun minusCounterCompleteTasks() {
        counterCompleteTasks--
    }

    fun deleteData() {
        currentTask?.deadlineDate = ""
    }

    fun getTaskByIndex(index: Int) = tasks.value?.get(index)
}