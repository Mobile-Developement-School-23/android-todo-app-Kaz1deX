package com.example.petruninkotlinyandex.data.dataSource.room.mapper

import com.example.petruninkotlinyandex.data.dataSource.room.TodoItemEntity
import com.example.petruninkotlinyandex.data.model.TodoItem

object MapperTodoItem {
    // Преобразование модели в сущность
    fun modelToEntity(todoItem: TodoItem): TodoItemEntity =
        TodoItemEntity(
            idTask = todoItem.idTask,
            checkBoxTaskText = todoItem.checkBoxTaskText,
            importance = todoItem.importance,
            currentDate = todoItem.currentDate,
            deadlineDate = todoItem.deadlineDate,
            isCompleted = todoItem.isCompleted
        )

    // Преобразование сущности в модель
    fun entityToModel(todoItemEntity: TodoItemEntity) =
        TodoItem(
            idTask = todoItemEntity.idTask,
            checkBoxTaskText = todoItemEntity.checkBoxTaskText,
            importance = todoItemEntity.importance,
            currentDate = todoItemEntity.currentDate,
            deadlineDate = todoItemEntity.deadlineDate,
            isCompleted = todoItemEntity.isCompleted
        )
}