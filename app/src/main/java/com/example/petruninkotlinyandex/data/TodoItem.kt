package com.example.petruninkotlinyandex.data

data class TodoItem(
    val id_task: String,
    val checkBoxTaskText: String,
    val importance: Importance,
    val isCompleted: Boolean,
    val creationDate: String
) {
    enum class Importance {
        LOW,
        NORMAL,
        HIGH
    }
}