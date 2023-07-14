package com.example.petruninkotlinyandex.data.model

// Модель элемента задачи
data class TodoItem(
    var idTask: Int = 0,              // Идентификатор задачи
    var checkBoxTaskText: String,     // Текст задачи для отображения в чекбоксе
    var importance: String = "",      // Важность задачи
    var deadlineDate: String = "",    // Дата крайнего срока выполнения задачи
    var isCompleted: Boolean = false, // Флаг, указывающий завершена ли задача
    var currentDate: String = "",     // Дата создания задачи
    var changingDate: String = "") {} // Дата изменения задачи
enum class Importance{
    LOW,
    NORMAL,
    HIGH
}