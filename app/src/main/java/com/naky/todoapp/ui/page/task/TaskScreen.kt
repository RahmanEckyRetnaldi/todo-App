package com.naky.todoapp.ui.page.task

import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.naky.todoapp.database.model.Priority
import com.naky.todoapp.database.model.ToDoTask
import com.naky.todoapp.utils.Action
import com.naky.todoapp.viewmodel.ToDoNotesViewModel

@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    toDoNotesViewModel: ToDoNotesViewModel,
    navigateToListScreen: (Action) -> Unit,
) {
    val title: String by toDoNotesViewModel.title
    val description: String by toDoNotesViewModel.description
    val priority: Priority by toDoNotesViewModel.priority
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                    } else {
                        if (toDoNotesViewModel.validateFields()) {
                            navigateToListScreen(action)
                        } else {
                            Toast.makeText(context, "Field Empty", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
    ) {
        TaskContent(
            title = title,
            onTitleChange = {
                toDoNotesViewModel.updateTitle(it)
            },
            description = description,
            onDescriptionChange = {
                toDoNotesViewModel.description.value = it
            },
            priority = priority,
            onPriorityChange ={
                toDoNotesViewModel.priority.value = it
            })
    }
}

