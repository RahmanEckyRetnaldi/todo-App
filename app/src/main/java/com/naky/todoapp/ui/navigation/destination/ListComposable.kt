package com.naky.todoapp.ui.navigation.destination

import android.annotation.SuppressLint
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.naky.todoapp.ui.page.ListScreen
import com.naky.todoapp.utils.Constants.LIST_ARGUMENT_KEY
import com.naky.todoapp.utils.Constants.LIST_SCREEN
import com.naky.todoapp.utils.toAction
import com.naky.todoapp.viewmodel.ToDoNotesViewModel

@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    toDoNotesViewModel: ToDoNotesViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = mutableStateListOf(
            navArgument(LIST_ARGUMENT_KEY) {
                type = NavType.StringType
            }
        )
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
        LaunchedEffect(key1 = action) {
            toDoNotesViewModel.action.value = action
        }

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            toDoNotesViewModel = toDoNotesViewModel
        )
    }
}