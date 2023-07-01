package com.example.petruninkotlinyandex.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.petruninkotlinyandex.R
import com.example.petruninkotlinyandex.data.dataSource.room.TodoItemEntity
import com.example.petruninkotlinyandex.data.model.TodoItem

//class TasksAdapter(private val tasksList: SharedFlow<List<TodoItemEntity>>): ListAdapter<TodoItemEntity, TasksAdapter.TasksViewHolder>(TaskDiffCallback()){
class TasksAdapter: ListAdapter<TodoItem, TasksAdapter.TasksViewHolder>(TaskDiffCallback()){
//    lateinit var tasksList: MutableLiveData<List<TodoItemEntity>>
    private var onItemClickListener: OnItemClickListener? = null

    // ViewHolder для элементов списка задач в RecyclerView
    class TasksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox_task)
        var buttonInfo: ImageView = itemView.findViewById(R.id.info_task)
    }

    // Вызывается, когда RecyclerView нуждается в новом ViewHolder для отображения элемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TasksViewHolder(layoutInflater.inflate(R.layout.item_list, parent, false))
    }

    // Связывает данные с элементом ViewHolder
    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
//        val todoItem: TodoItemEntity = tasksList.value?.get(position) ?: return
        val todoItem = getItem(position)

        // Устанавливаем текст и состояние флажка CheckBox на основе данных задачи
        holder.checkBox.text = todoItem.checkBoxTaskText
        holder.checkBox.isChecked = todoItem.isCompleted

        // Проверяем важность задачи и обновляем внешний вид CheckBox в соответствии с состоянием и важностью
        val importanceHigh: Boolean = todoItem.importance == "Высокий"
        updateTask(holder.checkBox, todoItem.isCompleted, importanceHigh)

        // Устанавливаем слушатель клика для CheckBox
        holder.checkBox.setOnClickListener {
            run {
                // Обновляем состояние CheckBox и вызываем слушатель onItemClick с соответствующей задачей
                updateTask(holder.checkBox, !todoItem.isCompleted, importanceHigh)
                todoItem.isCompleted = !todoItem.isCompleted
                onItemClickListener?.onItemClick(todoItem)
            }
        }

        // Устанавливаем слушатель клика для элемента списка задач
//        holder.itemView.setOnClickListener {
//            val transferData: Bundle = Bundle()
//            transferData.putInt("currentModel", position)
//            // Используем Navigation для перехода к фрагменту AddTaskFragment с передачей данных
//            Navigation.findNavController(it).navigate(R.id.action_mainScreenFragment_to_addTaskFragment, transferData)
//        }

        holder.buttonInfo.setOnClickListener {
            onItemClickListener?.onButtonInfoClick(todoItem)
//            transferData.putInt("currentModel", position)
            // Используем Navigation для перехода к фрагменту AddTaskFragment с передачей данных
//            Navigation.findNavController(it).navigate(R.id.action_mainScreenFragment_to_addTaskFragment, transferData)
        }
    }

    // Метод обновляет внешний вид CheckBox в зависимости от его состояния и важности задачи
    private fun updateTask(compoundButton: CompoundButton, newStatus: Boolean, isHighImportance: Boolean){
        // Если задача стала выполненной
        if (newStatus) {
            // Зачеркивание текста при выполнении задачи
            compoundButton.paintFlags = compoundButton.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            // Установка иконки выполненной задачи
            compoundButton.setButtonDrawable(R.drawable.checked)
        }
        // Если задача стала невыполненной
        else {
            // Возвращение обычного текста без зачеркивания
            compoundButton.paintFlags = compoundButton.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            compoundButton.setTextColor(ContextCompat.getColor(compoundButton.context, R.color.label_primary))

            // Установка иконки невыполненной задачи в зависимости от важности
            if (isHighImportance) compoundButton.setButtonDrawable(R.drawable.unchecked__red)
            else compoundButton.setButtonDrawable(R.drawable.unchecked_empty)
        }
    }

    fun getItemByPosition(position: Int): TodoItem {
        return getItem(position)
    }

    // Метод устанавливает слушатель OnItemClickListener для обработки кликов на элементах списка задач
    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    // Интерфейс определяет метод onItemClick() для обработки кликов на элементах списка задач
    interface OnItemClickListener {
        fun onItemClick(todoItem: TodoItem)
        fun onButtonInfoClick(todoItem: TodoItem)
    }

//    override fun submitList(list: List<TodoItemEntity>?) {
//        super.submitList(list?.let { ArrayList(it) })
//    }
}

class TaskDiffCallback: DiffUtil.ItemCallback<TodoItem>() {
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean = oldItem.idTask == newItem.idTask
    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean = oldItem == newItem
}
