package com.example.petruninkotlinyandex.data.dataSource.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoItemEntity::class], version = 1, exportSchema = false)
abstract class TodoItemDatabase: RoomDatabase() {
    abstract val todoItemDao: TodoItemDao
    companion object {
        // Создание экземпляра базы данных
        fun create(context: Context) = Room
            .databaseBuilder(
                context,
                TodoItemDatabase::class.java,
                "todoItem-database"
            )
            .build()
    }
}