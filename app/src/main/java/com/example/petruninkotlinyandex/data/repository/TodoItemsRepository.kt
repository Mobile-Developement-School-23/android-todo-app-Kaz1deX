package com.example.petruninkotlinyandex.data.repository

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import com.example.petruninkotlinyandex.data.dataSource.room.mapper.MapperTodoItem
import com.example.petruninkotlinyandex.data.dataSource.room.TodoItemDatabase
import com.example.petruninkotlinyandex.data.model.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class TodoItemsRepository(private val todoItemDatabase: TodoItemDatabase) {
    private val todoItemDao get() = todoItemDatabase.todoItemDao
    private val allTasks: Flow<List<TodoItem>> = todoItemDao.getAllTasks().map { list -> list.map { MapperTodoItem.entityToModel(it) } }
    private val uncompletedTasks: Flow<List<TodoItem>> = todoItemDao.getUncompletedTasks().map { list -> list.map { MapperTodoItem.entityToModel(it) } }

    fun getUncompletedTasks(): Flow<List<TodoItem>> = uncompletedTasks

    fun getAllTasks(): Flow<List<TodoItem>> = allTasks

    fun getCountCompleted(): LiveData<Int> {
        return todoItemDao.getCountCompleted()
    }

    suspend fun getTaskById(idTask: Int): TodoItem? {
        val todoItemEntity = todoItemDao.getTaskById(idTask)
        return if (todoItemEntity == null) {
            null
        }
        else MapperTodoItem.entityToModel(todoItemEntity)
    }

    suspend fun insert(todoItem: TodoItem) {
        // Форматирование даты
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        // Получение текущей даты в формате строки
        val dateString = dateFormat.format(Calendar.getInstance().time)

        // Установка текущей даты в задачу
        todoItem.currentDate = dateString

        todoItemDao.insert(MapperTodoItem.modelToEntity(todoItem))
    }

    suspend fun updateTask(todoItem: TodoItem) {
        todoItemDao.updateTask(MapperTodoItem.modelToEntity(todoItem))
    }

    suspend fun delete(todoItem: TodoItem) {
        todoItemDao.delete(MapperTodoItem.modelToEntity(todoItem))
    }
}