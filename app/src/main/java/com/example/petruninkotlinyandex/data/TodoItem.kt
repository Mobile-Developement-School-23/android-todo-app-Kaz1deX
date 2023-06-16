package com.example.petruninkotlinyandex.data

// Модель элемента задачи
data class TodoItem(
    var checkBoxTaskText: String,     // Текст задачи для отображения в чекбоксе
    var importance: String,           // Важность задачи
    var deadlineDate: String = "",    // Дата крайнего срока выполнения задачи
    var idTask: String = "0",         // Идентификатор задачи
    var isCompleted: Boolean = false, // Флаг, указывающий, завершена ли задача
    var currentDate: String = "") {}  // Дата создания задачи