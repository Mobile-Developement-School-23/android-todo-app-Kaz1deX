package com.example.petruninkotlinyandex.data.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

// Модель элемента задачи
@Entity(tableName = "task_table")
data class TodoItemEntity(
    var checkBoxTaskText: String,     // Текст задачи для отображения в чекбоксе
    var importance: String,           // Важность задачи
    var deadlineDate: String = "",    // Дата крайнего срока выполнения задачи
    var isCompleted: Boolean = false, // Флаг, указывающий, завершена ли задача
    @PrimaryKey(autoGenerate = true) var idTask: Int = 0,         // Идентификатор задачи
    var currentDate: String = "") {}  // Дата создания задачи