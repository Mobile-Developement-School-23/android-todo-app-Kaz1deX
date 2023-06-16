package com.example.petruninkotlinyandex.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petruninkotlinyandex.repositories.TodoItemsRepository

open class TaskViewModel: ViewModel() {
    private val todoItemsRepository = TodoItemsRepository()
    private var tasks: MutableLiveData<List<TodoItem>> = todoItemsRepository.getTasks()

    // Текущая задача, с которой необходимо работать
    private var currentTask: TodoItem? = null

    // Состояние нажатия кнопки, скрывающей выполненные задачи
    private var eyeIsVisibility: Boolean = true

    // Счетчик выполненных задач
    private var counterCompleteTasks: Int = 0

    // Получение списка задач
    fun getTasks(): MutableLiveData<List<TodoItem>> {
        return tasks
    }

    // Добавление задачи в репозиторий
    fun addTaskToRepository(todoItem: TodoItem) {
        todoItemsRepository.addTaskToRepository(todoItem)
    }

    // Удаление задачи из репозитория
    fun deleteTaskFromRepository(todoItem: TodoItem) {
        todoItemsRepository.deleteTaskFromRepository(todoItem)

        // Если задача завершена, уменьшаем счетчик завершенных задач
        if (todoItem.isCompleted) {
            minusCounterCompleteTasks()
        }
    }

    // Установка текущей задачи
    fun setCurrentTask(todoItem: TodoItem) {
        currentTask = todoItem
    }

    // Очистка текущей задачи
    fun clearCurrentTask() {
        currentTask = null
    }

    // Получение задачи по позиции
    fun getTaskByPosition(position: Int): TodoItem {
        return todoItemsRepository.getTaskByPosition(position)
    }

    // Получение текущей задачи
    fun getCurrentTask() = currentTask

    // Скрытие завершенных задач
    fun hideCompleteTasks() {
        tasks.value = todoItemsRepository.getTasks().value?.filter { !it.isCompleted}
    }

    // Показ всех задач
    fun showAllTasks() {
        tasks.value = todoItemsRepository.getAllTasks().value
    }

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
    fun getTaskByIndex(index: Int) = tasks.value?.get(index)
}