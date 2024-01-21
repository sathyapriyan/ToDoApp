package com.example.todoapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.data.room.entity.ToDoData
import kotlinx.coroutines.flow.Flow

///////////////////////////////////////////////////////////////////////////////////////////////

// Created by Srinivasan Jayakumar on 07.March.2023:14:07

///////////////////////////////////////////////////////////////////////////////////////////////

@Dao
interface ToDoDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMaster(masterData: List<ToDoData>)

    @Query("SELECT COUNT(*) FROM  todo_data_table")
    suspend fun getToDoDataCount(): Int

}