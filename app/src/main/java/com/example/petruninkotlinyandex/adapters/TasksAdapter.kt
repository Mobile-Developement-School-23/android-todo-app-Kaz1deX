package com.example.petruninkotlinyandex.adapters

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.data.TodoItem
import com.example.petruninkotlinyandex.viewHolder.TasksViewHolder

class TasksAdapter: RecyclerView.Adapter<TasksViewHolder>() {
    lateinit var tasksList: List<TodoItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TasksViewHolder(layoutInflater.inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount() = tasksList.size

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.onBind(tasksList[position])
        val todoItem: TodoItem = tasksList[position]
        holder.getCheckBox().text = todoItem.checkBoxTaskText
        holder.getCheckBox().isChecked = todoItem.isCompleted

        val importanceHigh: Boolean = todoItem.importance == "Высокий"
        updateTask(holder.getCheckBox(), todoItem.isCompleted, importanceHigh)
        holder.getCheckBox().setOnCheckedChangeListener { compoundButton, isChecked ->
            run {
                updateTask(compoundButton, isChecked, importanceHigh)
                todoItem.isCompleted = isChecked
            }
        }

        holder.itemView.setOnClickListener {
            val transferData: Bundle = Bundle()
            transferData.putInt("currentModel", position)
            Navigation.findNavController(it).navigate(R.id.action_mainScreenFragment_to_addTaskFragment, transferData)
        }
    }
    private fun updateTask(compoundButton: CompoundButton, newStatus: Boolean, isHighImportance: Boolean){
        if (newStatus) {
            compoundButton.paintFlags = compoundButton.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            //compoundButton.setTextColor(ContextCompat.getColor(compoundButton.context, R.color.gray_checkbox))
            compoundButton.setButtonDrawable(R.drawable.checked)
        }
        else {
            compoundButton.paintFlags = compoundButton.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
//            compoundButton.setTextColor(ContextCompat.getColor(compoundButton.context, R.color.white))
            compoundButton.setTextColor(ContextCompat.getColor(compoundButton.context, R.color.black))

            if (isHighImportance) compoundButton.setButtonDrawable(R.drawable.unchecked__red)
            else compoundButton.setButtonDrawable(R.drawable.unchecked_empty)
        }
    }
}