package com.example.petruninkotlinyandex.data.dataSource.network.retrofit

import com.example.petruninkotlinyandex.data.dataSource.network.api.TodoItemAPI
import com.example.petruninkotlinyandex.utils.Constants.BASE_URL
import com.example.petruninkotlinyandex.utils.Constants.TOKEN_API
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface RetrofitSource {
    // Создание Retrofit-сервиса
    fun makeRetrofitService(): TodoItemAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(makeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoItemAPI::class.java)
    }

    // Создание OkHttpClient для использования с Retrofit
    private fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(makeLoggingInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    // Создание HttpLoggingInterceptor для логирования сетевых запросов и ответов
    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    // Добавление заголовка авторизации к исходному запросу
    private fun addAuthorizationHeader(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $TOKEN_API")
            .build()
        return chain.proceed(newRequest)
    }
}