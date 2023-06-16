package com.example.petruninkotlinyandex.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.petruninkotlinyandex.data.TodoItem

// Дополнительное задание (использовать не удалось)
class DiffUtilTasks(
    private val oldTasks: List<TodoItem>,
    private val newTasks: List<TodoItem>
    ): DiffUtil.Callback() {

    // Возвращает размер старого списка задач
    override fun getOldListSize() = oldTasks.size

    // Возвращает размер нового списка задач
    override fun getNewListSize() = newTasks.size

    // Проверяет, являются ли элементы с одинаковыми позициями
    // в старом и новом списке задач одним и тем же элементом
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTasks[oldItemPosition].idTask == newTasks[newItemPosition].idTask
    }

    // Проверяет, имеют ли элементы с одинаковыми позициями
    // в старом и новом списке задач одинаковое содержимое
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldTasks[oldItemPosition]
        val newItem = newTasks[newItemPosition]
        return oldItem == newItem
    }
}