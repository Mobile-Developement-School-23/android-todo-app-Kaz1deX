package com.example.petruninkotlinyandex.ui.viewModel

import android.text.format.DateFormat
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petruninkotlinyandex.data.dataBase.TodoItemEntity
import com.example.petruninkotlinyandex.data.model.TodoItem
import com.example.petruninkotlinyandex.data.repository.TodoItemsRepository
import com.example.petruninkotlinyandex.locateLazy
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.coroutineContext

open class TaskViewModel: ViewModel() {
    private val todoItemsRepository: TodoItemsRepository by locateLazy()

    val tasks = todoItemsRepository.getAllTasks().asLiveDataFlow()

    fun insert(todoItem: TodoItemEntity) {
        viewModelScope.launch {
            todoItemsRepository.insert(todoItem)
        }
    }

    // Добавление задачи в репозиторий
//    fun addTaskToRepository(todoItem: TodoItem) {
//        todoItemsRepository.addTaskToRepository(todoItem)
//    }

    fun delete(todoItem: TodoItemEntity) {
        viewModelScope.launch {
            todoItemsRepository.delete(todoItem)
            if (todoItem.isCompleted) minusCounterCompleteTasks()
        }
    }

    // Удаление задачи из репозитория
//    fun deleteTaskFromRepository(todoItem: TodoItem) {
//        todoItemsRepository.deleteTaskFromRepository(todoItem)
//
//        // Если задача завершена, уменьшаем счетчик завершенных задач
//        if (todoItem.isCompleted) {
//            minusCounterCompleteTasks()
//        }
//    }

    // Текущая задача, с которой необходимо работать
    private var currentTask: TodoItemEntity? = null

    private fun <T> Flow<T>.asLiveDataFlow() = shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

//    private var tasks: MutableLiveData<List<TodoItemEntity>> = todoItemsRepository.getTasks()


    // Состояние нажатия кнопки, скрывающей выполненные задачи
    private var eyeIsVisibility: Boolean = true

    // Счетчик выполненных задач
    private var counterCompleteTasks: Int = 0

    // Получение списка задач
//    fun getTasks(): MutableLiveData<List<TodoItemEntity>> {
//        return tasks
//    }

    // Установка текущей задачи
    fun setCurrentTask(todoItem: TodoItemEntity) {
        currentTask = todoItem
    }

    // Очистка текущей задачи
    fun clearCurrentTask() {
        currentTask = null
    }

    // Получение задачи по позиции
//    fun getTaskByPosition(position: Int): TodoItem {
//        return todoItemsRepository.getTaskByPosition(position)
//    }

    // Получение текущей задачи
    fun getCurrentTask() = currentTask

    // Скрытие завершенных задач
//    fun hideCompleteTasks() {
//        tasks.value = todoItemsRepository.getTasks().value?.filter { !it.isCompleted}
//    }

    // Показ всех задач
//    fun showAllTasks() {
//        tasks.value = todoItemsRepository.getAllTasks().value
//    }

    // Получение состояния нажатия кнопки, скрывающей выполненные задачи
    fun getEyeVisibility(): Boolean {
        return eyeIsVisibility
    }

    // Установка состояния нажатия кнопки, скрывающей выполненные задачи
    fun setEyeVisibility(visibility: Boolean) {
        eyeIsVisibility = visibility
    }

    // Получение количества выполненных задач
    fun getCounterCompleteTasks(): Int {
        return counterCompleteTasks
    }

    // Увеличение количества выполненных задач
    fun plusCounterCompleteTasks() {
        counterCompleteTasks++
    }

    // Уменьшение количества выполненных задач
    fun minusCounterCompleteTasks() {
        counterCompleteTasks--
    }

    // Удаление даты дедлайна
    fun deleteDate() {
        currentTask?.deadlineDate = ""
    }

    // Получение задачи по индексу
//    fun getTaskByIndex(index: Int) = tasks.value?.get(index)
}