package com.naky.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.naky.todoapp.ui.navigation.ToDoNotesNavigation
import com.naky.todoapp.ui.theme.TodoAppTheme
import com.naky.todoapp.viewmodel.ToDoNotesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var  navController: NavHostController
    private val toDoNotesViewModel : ToDoNotesViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                setContent {
                    TodoAppTheme() {
                        Surface(color = MaterialTheme.colors.background) {
                            navController = rememberNavController()
                            ToDoNotesNavigation(
                                navHostController = navController,
                                toDoNotesViewModel = toDoNotesViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

