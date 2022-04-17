package com.naky.todoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.naky.todoapp.database.model.ToDoTask


@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao() : ToDoDao
    
}