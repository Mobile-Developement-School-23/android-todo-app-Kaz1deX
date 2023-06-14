package com.example.petruninkotlinyandex.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.MutableLiveData
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.data.TodoItem

class TodoItemsRepository {
    private val listTasks: MutableLiveData<List<TodoItem>> = MutableLiveData<List<TodoItem>>()
    private var currentIdTasks: String = "0"
    init {
        listTasks.value = ArrayList()
        addTaskToRepository(TodoItem("Купить что-то", "Низкий"))
        addTaskToRepository(TodoItem("Погулять на улице", "Нет"))
        addTaskToRepository(TodoItem("Написать доклад", "Высокий"))
        addTaskToRepository(TodoItem("Записаться на встречу с очень важным человеком, " +
                "главное не забыть, а то  будет очень плохо, прям ооочень", "Высокий"))
        addTaskToRepository(TodoItem("Убраться в квартире1", "Низкий"))
        addTaskToRepository(TodoItem("Убраться в кавартире2", "Нет"))
        addTaskToRepository(TodoItem("Убраться в квартире3", "Низкий"))
        addTaskToRepository(TodoItem("Убраться в квартире4", "Низкий"))
        addTaskToRepository(TodoItem("Убраться в квартире5", "Нет"))
        addTaskToRepository(TodoItem("Убраться в квартире6", "Низкий"))
    }
    fun getTasks(): MutableLiveData<List<TodoItem>> {
        return listTasks
    }
    fun addTaskToRepository(todoItem: TodoItem) {
        todoItem.idTask = currentIdTasks
        currentIdTasks = (currentIdTasks.toInt() + 1).toString()
//        val currentList: MutableList<TodoItem> = listTasks.value?.toMutableList() ?: mutableListOf()
//        currentList.add(todoItem)
//        listTasks.postValue(currentList)

        listTasks.value = listTasks.value?.plus(todoItem)
    }
    fun deleteTaskFromRepository(position: Int) {
//        listTasks.removeAt(position)
    }
    fun getTaskById(position: Int): TodoItem {
        val currentList: MutableList<TodoItem> = listTasks.value?.toMutableList() ?: mutableListOf()
        return currentList[position]
    }
}