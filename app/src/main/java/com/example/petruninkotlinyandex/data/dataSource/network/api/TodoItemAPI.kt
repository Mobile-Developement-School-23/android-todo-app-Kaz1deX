package com.example.petruninkotlinyandex.data.dataSource.network.api

import com.example.petruninkotlinyandex.data.dataSource.network.entities.TodoItemListResponse
import com.example.petruninkotlinyandex.data.dataSource.network.entities.TodoItemElementRequest
import com.example.petruninkotlinyandex.data.dataSource.network.entities.TodoItemElementResponse
import com.example.petruninkotlinyandex.data.dataSource.network.entities.TodoItemListRequest
import retrofit2.Response
import retrofit2.http.*

interface TodoItemAPI {
    // Получение списка задач
    @GET("list")
    suspend fun getList(@Header("Authorization") token: String): Response<TodoItemListResponse>

    // Обновление списка задач
    @PATCH("list")
    suspend fun updateList(
        @Header("Authorization") token: String,
        @Header("X-Last-Known-Revision") lastKnownRevision: Int,
        @Body body: TodoItemListRequest
    ): Response<TodoItemListRequest>

    // Получение задачи по её идентификатору
    @GET("list/{id}")
    suspend fun getTaskById(@Path("id") itemId: String): Response<TodoItemElementResponse>

    // Добавление новой задачи в список
    @POST("list")
    suspend fun addTask(
        @Header("Authorization") token: String,
        @Header("X-Last-Known-Revision") lastKnownRevision: Int,
        @Body newItem: TodoItemElementRequest
    ): Response<TodoItemElementResponse>

    // Обновление существующей задачи
    @PUT("list/{id}")
    suspend fun updateTask(
        @Header("Authorization") token: String,
        @Header("X-Last-Known-Revision") lastKnownRevision: Int,
        @Path("id") itemId: String,
        @Body body: TodoItemElementRequest
    ): Response<TodoItemElementResponse>

    // Удаление задачи из списка
    @DELETE("list/{id}")
    suspend fun deleteTask(
        @Header("Authorization") token: String,
        @Header("X-Last-Known-Revision") lastKnownRevision: Int,
        @Path("id") itemId: String,
    ): Response<TodoItemElementResponse>
}