package com.example.petruninkotlinyandex.adapters

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.data.TodoItem

class TasksAdapter: RecyclerView.Adapter<TasksAdapter.TasksViewHolder>(){
    lateinit var tasksList: MutableLiveData<List<TodoItem>>
    private var onItemClickListener: OnItemClickListener? = null

    class TasksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        //    private val binding = ItemListBinding.bind(itemView)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox_task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TasksViewHolder(layoutInflater.inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int {
        if(tasksList.value != null)
            return tasksList.value!!.size
        return 0
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val todoItem: TodoItem = tasksList.value?.get(position) ?: return

        holder.checkBox.text = todoItem.checkBoxTaskText
        holder.checkBox.isChecked = todoItem.isCompleted

        val importanceHigh: Boolean = todoItem.importance == "Высокий"
        updateTask(holder.checkBox, todoItem.isCompleted, importanceHigh)

        holder.checkBox.setOnClickListener {
            run {
                updateTask(holder.checkBox, !todoItem.isCompleted, importanceHigh)
                todoItem.isCompleted = !todoItem.isCompleted
                onItemClickListener?.onItemClick(todoItem)
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

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
    interface OnItemClickListener {
        fun onItemClick(todoItem: TodoItem)
    }
}