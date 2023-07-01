package com.example.petruninkotlinyandex.data.dataSource.network.entities

import com.google.gson.annotations.SerializedName

data class TodoItemElementRequest(
    @SerializedName("element")
    val element: TodoItemNetworkEntity
)