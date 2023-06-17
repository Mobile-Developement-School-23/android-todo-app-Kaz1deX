package com.example.petruninkotlinyandex.repositories

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.MutableLiveData
import com.example.petruninkotlinyandex.data.TodoItem
import java.util.*
import kotlin.collections.ArrayList

class TodoItemsRepository {
    // Список задач
    private val listTasks: MutableLiveData<List<TodoItem>> = MutableLiveData<List<TodoItem>>()
    // Второй список задач, так как в TaskViewModel в listTasks будут записываться только невыполненные задачи
    private val allTasks: MutableLiveData<List<TodoItem>> = MutableLiveData<List<TodoItem>>()
    // Идентификатор задачи
    private var currentIdTasks: String = "0"

    init {
        listTasks.value = ArrayList()
        // Добавление тестовых задач в репозиторий
        addTaskToRepository(TodoItem("Убраться в квартире", "Высокий"))
        addTaskToRepository(TodoItem("Сходить на рыбалку", "Нет"))
        addTaskToRepository(TodoItem("Очень важно купить тот самый чайник по скидки, " +
                "а то потом будет дорого стоит, а ведь он такоц красивый", "Высокий"))
        addTaskToRepository(TodoItem("Купить новый учебник для школы", "Высокий"))
        addTaskToRepository(TodoItem("Поспать", "Нет", isCompleted=true))
        addTaskToRepository(TodoItem("Продлить подписку на облачный сервис вовремя", "Низкий"))
        addTaskToRepository(TodoItem("Купить что-то", "Высокий", deadlineDate="18 июня 2023"))
        addTaskToRepository(TodoItem("Погулять на улице", "Нет", isCompleted=true))
        addTaskToRepository(TodoItem("Записаться на встречу с очень важным человеком, " +
                "главное не забыть, а то  будет очень плохо, прям ооочень", "Высокий"))
        addTaskToRepository(TodoItem("Написать доклад", "Высокий", isCompleted=true, currentDate="16 июня 2023"))
    }

    // Возвращает список задач в виде MutableLiveData
    fun getTasks(): MutableLiveData<List<TodoItem>> {
        return listTasks
    }

    // Возвращает второй список задач в виде MutableLiveData
    fun getAllTasks(): MutableLiveData<List<TodoItem>> {
        return allTasks
    }

    // Добавляет задачу в репозиторий
    fun addTaskToRepository(todoItem: TodoItem) {
        // Форматирование даты
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        // Получение текущей даты в формате строки
        val dateString = dateFormat.format(Calendar.getInstance().time)
        // Установка текущей даты в задачу
        todoItem.currentDate = dateString

        // Установка идентификатора задачи
        todoItem.idTask = currentIdTasks
        // Увеличение идентификатора на 1
        currentIdTasks = (currentIdTasks.toInt() + 1).toString()

        val tempList = listTasks.value?.toMutableList() ?: mutableListOf()
        val tempAllList = allTasks.value?.toMutableList() ?: mutableListOf()

        // Добавление задачи в начало списка задач
        tempList.add(0, todoItem)
        tempAllList.add(0, todoItem)

        // Установка обновленного списка задач
        listTasks.value = tempList
        allTasks.value = tempAllList
    }

    // Удаляет задачу из репозитория
    fun deleteTaskFromRepository(todoItem: TodoItem) {
        listTasks.value = listTasks.value?.minus(todoItem)
        allTasks.value = allTasks.value?.minus(todoItem)
    }

    // Возвращает задачу по позиции в списке
    fun getTaskByPosition(position: Int): TodoItem {
        val currentList: MutableList<TodoItem> = listTasks.value?.toMutableList() ?: mutableListOf()
        return currentList[position]
    }
}