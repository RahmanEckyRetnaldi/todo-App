package com.naky.todoapp.ui.navigation

import androidx.navigation.NavHostController
import com.naky.todoapp.utils.Action
import com.naky.todoapp.utils.Constants.LIST_SCREEN

class Screens (navHostController: NavHostController){
    val list : (Action) -> Unit = {
        action ->
        navHostController.navigate("list/${action.name}"){
            popUpTo(LIST_SCREEN){
                inclusive = true
            }
        }
    }
    val task : (Int) -> Unit ={
        taskId ->
        navHostController.navigate("task/$taskId")
    }
}