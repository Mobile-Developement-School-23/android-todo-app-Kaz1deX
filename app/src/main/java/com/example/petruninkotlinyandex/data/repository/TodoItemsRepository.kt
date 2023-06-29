package com.example.petruninkotlinyandex.data.repository

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.petruninkotlinyandex.data.dataBase.TodoItemDatabase
import com.example.petruninkotlinyandex.data.dataBase.TodoItemEntity
import com.example.petruninkotlinyandex.data.model.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class TodoItemsRepository(private val todoItemDatabase: TodoItemDatabase) {
    // Идентификатор задачи
//    private var currentIdTasks = 0
    private val todoItemDao get() = todoItemDatabase.todoItemDao
    // Текущая задача, с которой необходимо работать
    private var currentTask: TodoItemEntity? = null
    // Состояние нажатия кнопки, скрывающей выполненные задачи
    private var eyeIsVisibility = true

//    private var tasks: Flow<List<TodoItemEntity>> = todoItemDao.getAllTasks()

    // Счетчик выполненных задач
//    private var counterCompleteTasks = 0

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
//    private val flowOfItems: Flow<List<TodoItemEntity>> = todoItemDao.getAllTasks()
//    private lateinit var itemList: List<TodoItemEntity>

    fun getUncompletedTasks(): Flow<List<TodoItemEntity>> = todoItemDao.getUncompletedTasks()
//    fun getAllTasks(): Flow<List<TodoItemEntity>> = todoItemDao.getAllTasks()
    fun getAllTasks(): Flow<List<TodoItemEntity>> = todoItemDao.getAllTasks()
    fun getCountCompleted(): LiveData<Int> {
        return todoItemDao.getCountCompleted()
    }


//    fun getAllTasks(): List<TodoItemEntity> {
//        coroutineScope.launch {
//            itemList = convertFlowToList(flowOfItems)
//        }
//        return itemList
//    }
//
//    private suspend fun convertFlowToList(flow: Flow<List<TodoItemEntity>>): List<TodoItemEntity> {
//        val resultList = mutableListOf<TodoItemEntity>()
//        flow.collect { list ->
//            resultList.addAll(list)
//        }
//        return resultList
//    }

    suspend fun insert(todoItem: TodoItemEntity) {
        // Форматирование даты
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        // Получение текущей даты в формате строки
        val dateString = dateFormat.format(Calendar.getInstance().time)
        // Установка текущей даты в задачу
        todoItem.currentDate = dateString

//        // Установка идентификатора задачи
//        todoItem.idTask = currentIdTasks
//        // Увеличение идентификатора на 1
//        currentIdTasks += 1

        todoItemDao.insert(todoItem)
    }

    suspend fun updateTask(todoItem: TodoItemEntity) {
        todoItemDao.updateTask(todoItem)
    }
    suspend fun delete(todoItem: TodoItemEntity) {
        todoItemDao.delete(todoItem)
//        if (todoItem.isCompleted) minusCounterCompleteTasks()
    }

//    // Увеличение количества выполненных задач
//    fun plusCounterCompleteTasks() {
//        counterCompleteTasks++
//    }
//
//    // Уменьшение количества выполненных задач
//    fun minusCounterCompleteTasks() {
//        counterCompleteTasks--
//    }
//
//    // Получение количества выполненных задач
//    fun getCounterCompleteTasks(): Int {
//        return counterCompleteTasks
//    }

    // Установка состояния нажатия кнопки, скрывающей выполненные задачи
    fun setEyeVisibility(visibility: Boolean) {
        eyeIsVisibility = visibility
    }

    // Установка текущей задачи
    fun setCurrentTask(todoItem: TodoItemEntity) {
        currentTask = todoItem
    }

    // Очистка текущей задачи
    fun clearCurrentTask() {
        currentTask = null
    }

    // Получение текущей задачи
    fun getCurrentTask() = currentTask

    // Скрытие завершенных задач
//    fun hideCompleteTasks() {
//        tasks = todoItemDao.getUncompletedTasks()
////        tasks.value = todoItemsRepository.getTasks().value?.filter { !it.isCompleted}
//    }

    // Показ всех задач
    fun showAllTasks() {
//        tasks.value = todoItemsRepository.getAllTasks().value
    }

    // Получение состояния нажатия кнопки, скрывающей выполненные задачи
    fun getEyeVisibility(): Boolean {
        return eyeIsVisibility
    }

    // Удаление даты дедлайна
    fun deleteDate() {
        currentTask?.deadlineDate = ""
    }

    // Возвращает задачу по позиции в списке
//    fun getTaskByPosition(position: Int): TodoItem {
//        val currentList: MutableList<TodoItem> = listTasks.value?.toMutableList() ?: mutableListOf()
//        return currentList[position]
//    }
}