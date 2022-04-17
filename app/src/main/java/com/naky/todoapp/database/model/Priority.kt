package com.naky.todoapp.database.model

import androidx.compose.ui.graphics.Color
import com.naky.todoapp.ui.theme.HighPriorityColor
import com.naky.todoapp.ui.theme.LowPriorityColor
import com.naky.todoapp.ui.theme.MediumPriorityColor
import com.naky.todoapp.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)

}