package com.naky.todoapp.database.repository

import com.naky.todoapp.database.ToDoDao
import com.naky.todoapp.database.model.ToDoTask
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(
  private val toDoDao : ToDoDao
) {
    val getAllTask : Flow<List<ToDoTask>> = toDoDao.getAllTasks()
    val sortByLowPriority : Flow<List<ToDoTask>> = toDoDao.sortByLowPriority()
    val sortByHighPriority : Flow<List<ToDoTask>> = toDoDao.sortByHighPriority()

    fun getSelectedTask(taskId : Int) : Flow<ToDoTask>{
        return toDoDao.getSelectedTask(taskId)
    }

    suspend fun addTask(toDoTask: ToDoTask ){
        toDoDao.addTask(toDoTask)
    }

    suspend fun updateTask(toDoTask: ToDoTask){
        toDoDao.updateTask(toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask){
        toDoDao.deleteTask(toDoTask)
    }

    suspend fun deleteAllTask(){
        toDoDao.deleteAllTask()
    }
    fun searchDatabase(searchQuery : String) : Flow<List<ToDoTask>>{
        return toDoDao.searchDatabase(searchQuery)
    }
}