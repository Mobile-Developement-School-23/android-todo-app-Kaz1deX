package com.example.petruninkotlinyandex.data

data class TodoItem(var checkBoxTaskText: String, var importance: String, var deadlineDate: String = "") {
    var idTask: String = "0"
    var isCompleted: Boolean = false
    var currentDate: String = ""
}