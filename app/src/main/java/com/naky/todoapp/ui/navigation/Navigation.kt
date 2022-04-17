package com.naky.todoapp.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.naky.todoapp.ui.navigation.destination.listComposable
import com.naky.todoapp.ui.navigation.destination.taskComposable
import com.naky.todoapp.utils.Constants.LIST_SCREEN
import com.naky.todoapp.viewmodel.ToDoNotesViewModel

@ExperimentalMaterialApi
@Composable
fun ToDoNotesNavigation(
    navHostController: NavHostController,
    toDoNotesViewModel: ToDoNotesViewModel
) {
    val screen = remember(navHostController) { Screens(navHostController) }

    NavHost(
        navController = navHostController,
        startDestination = LIST_SCREEN
    ) {
        listComposable(
            navigateToTaskScreen = screen.task,
            toDoNotesViewModel = toDoNotesViewModel
        )
        taskComposable(
            toDoNotesViewModel = toDoNotesViewModel,
            navigateToListScreen = screen.list
        )
    }
}
