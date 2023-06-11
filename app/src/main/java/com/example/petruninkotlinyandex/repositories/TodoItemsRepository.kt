package com.example.petruninkotlinyandex.repositories

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.data.TodoItem

class TodoItemsRepository {
    fun getTasks(context: Context): List<TodoItem> {
        return buildList {
            val checkBoxTaskText = "Hello, Maxim"

            val numberOfTasks = (1..10).random()
            for(i in 0..10)
                add(TodoItem(checkBoxTaskText))
        }
    }
}