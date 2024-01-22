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
    suspend fun saveData(todoData: List<ToDoData>)

    @Query("SELECT COUNT(*) FROM  todo_data_table")
    fun observeToDoDataCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM  todo_data_table")
    fun getToDoDataCount(): Int

    @Query("SELECT COUNT(*) FROM  todo_data_table WHERE userId LIKE :userid AND completed LIKE :isCompleted")
    fun getToDoCompetedCountByUser(userid:Int,isCompleted:Boolean): Int

    @Query("SELECT * FROM todo_data_table")
    fun getAllToDoList(): Flow<List<ToDoData>>

    @Query("SELECT * FROM todo_data_table WHERE completed LIKE :isCompleted")
    fun getToDoListByCompletedStatus(isCompleted:Boolean): Flow<List<ToDoData>>

    @Query("SELECT DISTINCT userId FROM todo_data_table")
    fun getUserIdList(): List<Int>

    @Query("SELECT * FROM todo_data_table WHERE userId LIKE :userId")
    fun getToDosByUserId(userId:Int): List<ToDoData>

    @Query("DELETE FROM todo_data_table")
    fun deleteStories(): Int

}