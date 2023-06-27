package com.example.petruninkotlinyandex

import android.app.Application
import android.content.Context
import com.example.petruninkotlinyandex.data.dataBase.TodoItemDatabase
import com.example.petruninkotlinyandex.data.repository.TodoItemsRepository
import kotlin.reflect.KClass

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        ServiceLocator.register<Context>(this)
        ServiceLocator.register(TodoItemDatabase.create(locate()))
        ServiceLocator.register(TodoItemsRepository(locate()))
    }
}

object ServiceLocator {
    private val instances = mutableMapOf<KClass<*>, Any>()

    inline fun <reified T: Any> register(instance: T) = register(T::class, instance)

    fun <T: Any> register(kClass: KClass<T>, instance: T) {
        instances[kClass] = instance
    }

    @Suppress("UNCHECKED_CAST")
    fun <T: Any> get(kClass: KClass<T>): T = instances[kClass] as T
}

inline fun <reified T: Any> locate() = ServiceLocator.get(T::class)
inline fun <reified T: Any> locateLazy(): Lazy<T> = lazy { ServiceLocator.get(T::class) }