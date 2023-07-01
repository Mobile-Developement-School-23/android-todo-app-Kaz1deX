package com.example.petruninkotlinyandex.data.dataSource.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {
    // Добавить задачу в базу данных
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todoItem: TodoItemEntity)

    // Удалить задачу из базы данных
    @Delete
    suspend fun delete(todoItem: TodoItemEntity)

    // Обновить данные задачи
    @Update
    suspend fun updateTask(todoItem: TodoItemEntity)

    // Получить все задачи из базы данных
    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<TodoItemEntity>>

    // Получить только невыполненные задачи
    @Query("SELECT * FROM task_table WHERE isCompleted = 0")
    fun getUncompletedTasks(): Flow<List<TodoItemEntity>>

    // Получить количество выполненных задач
    @Query("SELECT COUNT(*) FROM task_table WHERE isCompleted = 1")
    fun getCountCompleted(): LiveData<Int>

    // Получить задачу по конкретному идентификатору
    @Query("SELECT * FROM task_table WHERE idTask = :id")
    suspend fun getTaskById(id: Int): TodoItemEntity?
}