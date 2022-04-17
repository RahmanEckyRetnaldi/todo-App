package com.naky.todoapp.ui.page

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.naky.todoapp.ui.page.task.ListContent
import com.naky.todoapp.ui.theme.fabBackgroundColor
import com.naky.todoapp.utils.Action
import com.naky.todoapp.utils.SearchAppBarState
import com.naky.todoapp.viewmodel.ToDoNotesViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int ) -> Unit,
    toDoNotesViewModel: ToDoNotesViewModel
) {
    LaunchedEffect(key1 = true) {
        toDoNotesViewModel.getAllTasks()
        toDoNotesViewModel.readSortState()
    }
    val action by toDoNotesViewModel.action
    val allTasks by toDoNotesViewModel.allTasks.collectAsState()
    val sortState by toDoNotesViewModel.sortState.collectAsState()
    val lowPriorityTasks by toDoNotesViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by toDoNotesViewModel.highPriorityTasks.collectAsState()
    val searchedTasks by toDoNotesViewModel.searchedTask.collectAsState()
    val searchAppBarState : SearchAppBarState by toDoNotesViewModel.searchAppBarState
    val searchTextState : String by toDoNotesViewModel.searchTextState
    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = {toDoNotesViewModel.handleDatabaseActions(action = action)},
        onUndoClicked = {
            toDoNotesViewModel.action.value = it
        },
        taskTitle = toDoNotesViewModel.title.value ,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                toDoNotesViewModel = toDoNotesViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
            ListContent(
                allTasks = allTasks,
                searchedTasks = searchedTasks,
                lowPriorityTasks = lowPriorityTasks,
                highPriorityTasks= highPriorityTasks,
                sortState = sortState,
                searchAppBarState = searchAppBarState,
                onSwipeToDelete = {action,task ->
                    toDoNotesViewModel.action.value = action
                    toDoNotesViewModel.updateTaskField(selectedTask = task)

                },
                navigateToTaskScreen = navigateToTaskScreen
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int ) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "",
            tint = Color.White
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle : String,
    action: Action
) {
    handleDatabaseActions()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action = action,taskTitle = taskTitle),
                    actionLabel = setActionLabel(action = action)
                )
                undoDeletedTask(action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked)
            }
        }
    }
}

private fun setMessage(action: Action, taskTitle: String) : String {
    return when(action) {
        Action.DELETE_ALL -> "All Tasks Removed"
        else -> "${action.name}: $taskTitle"
    }
}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE" ) "UNDO" else "OK"
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked : (Action) -> Unit,
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}