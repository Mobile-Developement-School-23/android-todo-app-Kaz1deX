package com.example.petruninkotlinyandex.data.repository

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import com.example.petruninkotlinyandex.data.dataSource.network.retrofit.RetrofitSource
import com.example.petruninkotlinyandex.data.dataSource.room.mapper.MapperTodoItem
import com.example.petruninkotlinyandex.data.dataSource.room.TodoItemDatabase
import com.example.petruninkotlinyandex.data.model.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class TodoItemsRepository(private val todoItemDatabase: TodoItemDatabase) {
    // Идентификатор задачи
//    private var currentIdTasks = 0

    private val todoItemDao get() = todoItemDatabase.todoItemDao

    // Состояние нажатия кнопки, скрывающей выполненные задачи
    private var eyeIsVisibility = true

    private val allTasks: Flow<List<TodoItem>> = todoItemDao.getAllTasks().map { list -> list.map { MapperTodoItem.entityToModel(it) } }
    private val uncompletedTasks: Flow<List<TodoItem>> = todoItemDao.getUncompletedTasks().map { list -> list.map { MapperTodoItem.entityToModel(it) } }
//    private var tasks: Flow<List<TodoItemEntity>> = todoItemDao.getAllTasks()

    // Счетчик выполненных задач
//    private var counterCompleteTasks = 0

//    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
//    private val flowOfItems: Flow<List<TodoItemEntity>> = todoItemDao.getAllTasks()
//    private lateinit var itemList: List<TodoItemEntity>

    fun getUncompletedTasks(): Flow<List<TodoItem>> = uncompletedTasks
//    fun getAllTasks(): Flow<List<TodoItemEntity>> = todoItemDao.getAllTasks()

    fun getAllTasks(): Flow<List<TodoItem>> = allTasks

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

//        // Установка идентификатора задачи
//        todoItem.idTask = currentIdTasks
//        // Увеличение идентификатора на 1
//        currentIdTasks += 1

        todoItemDao.insert(MapperTodoItem.modelToEntity(todoItem))
    }

    suspend fun updateTask(todoItem: TodoItem) {
        todoItemDao.updateTask(MapperTodoItem.modelToEntity(todoItem))
    }
    suspend fun delete(todoItem: TodoItem) {
        todoItemDao.delete(MapperTodoItem.modelToEntity(todoItem))
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

//    // Установка текущей задачи
//    fun setCurrentTask(todoItem: TodoItem) {
//        currentTask = todoItem
//    }
//
//    // Получение текущей задачи
//    fun getCurrentTask() = currentTask

    // Очистка текущей задачи
//    fun clearCurrentTask() {
//        currentTask = null
//    }

    // Скрытие завершенных задач
//    fun hideCompleteTasks() {
//        tasks = todoItemDao.getUncompletedTasks()
////        tasks.value = todoItemsRepository.getTasks().value?.filter { !it.isCompleted}
//    }

    // Показ всех задач
//    fun showAllTasks() {
////        tasks.value = todoItemsRepository.getAllTasks().value
//    }

    // Получение состояния нажатия кнопки, скрывающей выполненные задачи
    fun getEyeVisibility(): Boolean {
        return eyeIsVisibility
    }

    // Возвращает задачу по позиции в списке
//    fun getTaskByPosition(position: Int): TodoItem {
//        val currentList: MutableList<TodoItem> = listTasks.value?.toMutableList() ?: mutableListOf()
//        return currentList[position]
//    }
}