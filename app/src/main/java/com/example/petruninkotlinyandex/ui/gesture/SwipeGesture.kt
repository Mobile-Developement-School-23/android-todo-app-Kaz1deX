package com.example.petruninkotlinyandex.ui.gesture

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.petruninkotlinyandex.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

// Абстрактный класс расширяет функциональность ItemTouchHelper.SimpleCallback
abstract class SwipeGesture(context: Context): ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT
) {
    // Цвет для фона при свайпе влево
    private val deleteColor = ContextCompat.getColor(context, R.color.color_red)

    // Иконка при свайпе влево
    private val deleteIcon = R.drawable.delete_white

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        // Создание объекта RecyclerViewSwipeDecorator для настройки отображения свайпа
        RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            // Установка цвета фона при свайпе влево
            .addSwipeLeftBackgroundColor(deleteColor)

            // Установка иконки при свайпе влево
            .addSwipeLeftActionIcon(deleteIcon)
            .create()
            .decorate()
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}