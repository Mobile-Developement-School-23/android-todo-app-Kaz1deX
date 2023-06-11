package com.example.petruninkotlinyandex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.data.TodoItem
import com.example.petruninkotlinyandex.viewHolder.TasksViewHolder

class TasksAdapter: RecyclerView.Adapter<TasksViewHolder>() {
    var tasksList = listOf<TodoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TasksViewHolder(layoutInflater.inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount() = tasksList.size

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.onBind(tasksList[position])
    }
}