package com.example.petruninkotlinyandex.data.dataBase

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
}