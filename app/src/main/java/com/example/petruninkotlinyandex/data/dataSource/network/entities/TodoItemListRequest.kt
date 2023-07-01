package com.example.petruninkotlinyandex.data.dataSource.network.entities

import com.google.gson.annotations.SerializedName

data class TodoItemListRequest(
    @SerializedName("status")
    val status: String,
    @SerializedName("list")
    val list: List<TodoItemNetworkEntity>
)
