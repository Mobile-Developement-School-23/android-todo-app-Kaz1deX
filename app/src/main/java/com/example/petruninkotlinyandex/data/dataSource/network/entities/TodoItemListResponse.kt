package com.example.petruninkotlinyandex.data.dataSource.network.entities

import com.google.gson.annotations.SerializedName

data class TodoItemListResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("list")
    val list: List<TodoItemNetworkEntity>,
    @SerializedName("revision")
    val revision: Int
)