package com.example.petruninkotlinyandex.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// Модель элемента задачи
@Entity(tableName = "task_table")
data class TodoItemEntity(
    @PrimaryKey(autoGenerate = true) var idTask: String = "0",         // Идентификатор задачи
    var checkBoxTaskText: String,     // Текст задачи для отображения в чекбоксе
    var importance: String,           // Важность задачи
    var deadlineDate: String = "",    // Дата крайнего срока выполнения задачи
    var isCompleted: Boolean = false, // Флаг, указывающий, завершена ли задача
    var currentDate: String = "") {}  // Дата создания задачи