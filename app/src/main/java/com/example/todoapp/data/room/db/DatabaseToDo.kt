package com.example.todoapp.data.room.db

import android.annotation.SuppressLint
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.core.util.constants.DATABASE_VERSION
import com.example.todoapp.data.room.dao.ToDoDataDao
import com.example.todoapp.data.room.entity.ToDoData

@SuppressLint("NewApi")
@Database(
    entities = [
        ToDoData::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class DatabaseToDo : RoomDatabase() {

    abstract fun toDoDataDao(): ToDoDataDao

}