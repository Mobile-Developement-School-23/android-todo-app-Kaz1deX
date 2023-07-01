package com.example.petruninkotlinyandex.data.dataSource.network.mapper

import com.example.petruninkotlinyandex.data.dataSource.network.entities.TodoItemNetworkEntity
import com.example.petruninkotlinyandex.data.model.TodoItem

object TodoItemMapper {
    // Преобразование модели в сущность для работы с сетью
    fun modelToEntity(todoItem: TodoItem) =
        TodoItemNetworkEntity(
            id = todoItem.idTask.toString(),
            text = todoItem.checkBoxTaskText,
            importance = todoItem.importance,
            isDone = todoItem.isCompleted,
            createdAt = todoItem.currentDate,
            deadline = todoItem.deadlineDate,
            changedAt = todoItem.changingDate
        )

    // Преобразование сущности в модель
    fun entityToModel(todoItemNetworkEntity: TodoItemNetworkEntity) =
        TodoItem(
            idTask = todoItemNetworkEntity.id.toInt(),
            checkBoxTaskText = todoItemNetworkEntity.text,
            importance = todoItemNetworkEntity.importance,
            isCompleted = todoItemNetworkEntity.isDone,
            currentDate = todoItemNetworkEntity.createdAt,
            deadlineDate = todoItemNetworkEntity.deadline,
            changingDate = todoItemNetworkEntity.changedAt
        )
}