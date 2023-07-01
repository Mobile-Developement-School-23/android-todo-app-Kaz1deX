package com.example.petruninkotlinyandex.data.dataSource.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Сущность элемента задачи
@Entity(tableName = "task_table")
data class TodoItemEntity(
    @PrimaryKey(autoGenerate = true) var idTask: Int = 0,                // Идентификатор задачи
    @ColumnInfo(name = "checkBoxTaskText") var checkBoxTaskText: String, // Текст задачи для отображения в чекбоксе
    @ColumnInfo(name = "importance") var importance: String,             // Важность задачи
    @ColumnInfo(name = "currentDate") var currentDate: String = "",      // Дата создания задачи
    @ColumnInfo(name = "deadlineDate") var deadlineDate: String = "",    // Дата крайнего срока выполнения задачи
    @ColumnInfo(name = "isCompleted") var isCompleted: Boolean = false,  // Флаг, указывающий, завершена ли задача
    @ColumnInfo(name = "changingDate") var changingDate: String = "") {} // Дата изменения задачи