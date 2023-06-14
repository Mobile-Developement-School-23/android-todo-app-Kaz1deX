package com.example.petruninkotlinyandex.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petruninkotlinyandex.data.TodoItem
import com.example.petruninkotlinyandex.repositories.TodoItemsRepository

open class TaskViewModel: ViewModel() {
//    val textTask: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val todoItemsRepository = TodoItemsRepository()
    private var tasks: MutableLiveData<List<TodoItem>> = todoItemsRepository.getTasks()
    private var currentTask: TodoItem? = null
    fun getTasks(): MutableLiveData<List<TodoItem>> {
        return tasks
    }
    fun addTaskToRepository(todoItem: TodoItem) {
        todoItemsRepository.addTaskToRepository(todoItem)
    }
    fun deleteTaskFromRepository(position: Int) {
        todoItemsRepository.deleteTaskFromRepository(position)
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
}