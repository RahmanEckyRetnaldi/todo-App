package com.naky.todoapp.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.naky.todoapp.database.model.Priority
import com.naky.todoapp.ui.theme.LARGE_PADDING
import com.naky.todoapp.ui.theme.PRIORITY_DROP_DOWN_HEIGHT
import com.naky.todoapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.naky.todoapp.ui.theme.Typography


@Composable
fun PriorityDropDown(
    modifier: Modifier = Modifier,
    priority: Priority,
    onPrioritySelected : (Priority) -> Unit
){
    val listPriority = listOf(
        Priority.LOW,
        Priority.MEDIUM,
        Priority.HIGH,
        Priority.NONE
    )
    var expanded by remember {
        mutableStateOf(false)
    }
    val angel : Float by animateFloatAsState(targetValue = if(expanded) 180f else 0f)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .height(PRIORITY_DROP_DOWN_HEIGHT)
            .clickable {
                expanded = true
            }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.surface.copy(
                    alpha = ContentAlpha.disabled
                ),
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .weight(1f),
        ){
            drawCircle(color = priority.color)
        }
        Text(
            text = priority.name,
            modifier = modifier
                .weight(8f),
            style = MaterialTheme.typography.subtitle2
        )
        IconButton(
            onClick = { expanded = true },
            modifier = modifier
                .alpha(ContentAlpha.medium)
                .rotate(angel)
                .weight(1.5f)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Arrow"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = modifier
                .fillMaxWidth(fraction = 0.94f)
        ) {
            listPriority.forEach {
                    priority ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onPrioritySelected(priority)
                    }
                ) {
                    PriorityItem(priority = priority)
                }
            }


        }

    }

}


@Composable
fun PriorityItem(
    priority: Priority
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier.size(PRIORITY_INDICATOR_SIZE),
        ){
            drawCircle(
                color = priority.color
            )
        }
        Text(
            text = priority.name,
            modifier = Modifier.padding(start = LARGE_PADDING),
            style = Typography.subtitle2,
            color = MaterialTheme.colors.onSurface
        )
    }
}