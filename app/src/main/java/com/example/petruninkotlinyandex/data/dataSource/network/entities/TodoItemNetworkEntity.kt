package com.example.petruninkotlinyandex.data.dataSource.network.entities

import com.google.gson.annotations.SerializedName

data class TodoItemNetworkEntity(
    @SerializedName("id") val id: String,
    @SerializedName("text") val text: String,
    @SerializedName("importance") val importance: String,
    @SerializedName("done") val isDone: Boolean,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("deadline") val deadline: String,
    @SerializedName("changingDate") val changedAt: String,
    @SerializedName("color") val color: String = "#FFFFFF",
    @SerializedName("last_updated_by") val device: String = "this"
) {

}