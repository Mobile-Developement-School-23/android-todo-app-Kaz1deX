package com.example.petruninkotlinyandex

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petruninkotlinyandex.data.TodoItem
import com.example.petruninkotlinyandex.repositories.TodoItemsRepository

open class TaskViewModel: ViewModel() {
    val textTask: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val todoItemsRepository = TodoItemsRepository()
    fun getTasks(context: Context): List<TodoItem> {
        return todoItemsRepository.getTasks(context)
    }
    fun addTaskToRepository(todoItem: TodoItem) {
        todoItemsRepository.addTask(todoItem)
    }
}