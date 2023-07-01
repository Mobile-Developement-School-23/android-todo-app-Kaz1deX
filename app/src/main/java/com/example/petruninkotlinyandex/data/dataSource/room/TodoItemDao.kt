package com.example.petruninkotlinyandex.data.dataSource.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todoItem: TodoItemEntity)

    @Delete
    suspend fun delete(todoItem: TodoItemEntity)

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<TodoItemEntity>>

    @Query("SELECT * FROM task_table WHERE isCompleted = 0")
    fun getUncompletedTasks(): Flow<List<TodoItemEntity>>

    @Query("SELECT COUNT(*) FROM task_table WHERE isCompleted = 1")
    fun getCountCompleted(): LiveData<Int>

    @Update
    suspend fun updateTask(todoItem: TodoItemEntity)

    @Query("SELECT * FROM task_table WHERE idTask = :id")
    suspend fun getTaskById(id: Int): TodoItemEntity?
}