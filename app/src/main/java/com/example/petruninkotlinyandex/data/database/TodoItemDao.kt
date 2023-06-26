package com.example.petruninkotlinyandex.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(todoItem: TodoItemEntity)

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<TodoItemEntity>>

    @Delete
    fun delete(todoItem: TodoItemEntity)
}