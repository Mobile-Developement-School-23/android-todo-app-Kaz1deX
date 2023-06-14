package com.example.petruninkotlinyandex.data

data class TodoItem(var checkBoxTaskText: String, var importance: String, val creationDate: String = "None") {
    var idTask: String = "0"
    var isCompleted: Boolean = false
}