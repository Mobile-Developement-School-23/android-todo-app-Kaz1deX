package com.example.petruninkotlinyandex.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoItemEntity::class], version = 1, exportSchema = false)
abstract class TodoItemDatabase: RoomDatabase() {
    abstract val todoItemDao: TodoItemDao
    companion object {
        fun create(context: Context) = Room.databaseBuilder(
            context,
            TodoItemDatabase::class.java,
            "todoitem-database"
        )
    }
}