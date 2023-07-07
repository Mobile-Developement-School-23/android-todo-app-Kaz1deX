package com.example.petruninkotlinyandex.data.dataSource.network.entities

import com.google.gson.annotations.SerializedName

data class TodoItemElementResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("element")
    val element:TodoItemNetworkEntity,
    @SerializedName("revision")
    val revision: Int
)