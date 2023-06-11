package com.example.petruninkotlinyandex.viewHolder

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.data.TodoItem
import com.example.petruninkotlinyandex.databinding.ItemListBinding

class TasksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val binding = ItemListBinding.bind(itemView)
//    private val checkBoxTask: CheckBox = itemView.findViewById(R.id.checkBox_task)

    fun onBind(todoItem: TodoItem) {
//        checkBoxTask.text = todoItem.checkBoxTaskText
        binding.checkBoxTask.text = todoItem.checkBoxTaskText
    }
}