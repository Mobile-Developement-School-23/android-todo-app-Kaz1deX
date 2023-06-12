package com.example.petruninkotlinyandex.repositories

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.data.TodoItem

class TodoItemsRepository {
    private val listTasks = mutableListOf(
        TodoItem("1", "Купить что-то", TodoItem.Importance.NORMAL, false, "11-06-2023"),
        TodoItem("2", "Погулять на улице", TodoItem.Importance.LOW, false, "10-06-2023"),
        TodoItem("3", "Написать доклад", TodoItem.Importance.HIGH, false, "09-06-2023"),
        TodoItem("4", "Записаться на встречу с очень важным человеком, главное не забыть, а то  будет очень плохо, прям ооочень",
            TodoItem.Importance.NORMAL, false, "11-06-2023"),
        TodoItem("5", "Убраться в кавартире", TodoItem.Importance.NORMAL, false, "11-06-2023"),
        TodoItem("6", "Убраться в кавартире", TodoItem.Importance.NORMAL, false, "11-06-2023"),
        TodoItem("7", "Убраться в кавартире", TodoItem.Importance.NORMAL, false, "11-06-2023"),
        TodoItem("8", "Убраться в кавартире", TodoItem.Importance.NORMAL, false, "11-06-2023"))

    fun getTasks(context: Context): List<TodoItem> {
        return listTasks

//        return buildList {
//            val checkBoxTaskText = "Hello, Maxim"
//
//            val numberOfTasks = (1..10).random()
//            for(i in 0..10)
//                add(TodoItem(checkBoxTaskText))
//        }
    }

    fun addTask(todoItem: TodoItem) {
        listTasks.add(0, todoItem)
    }
}