package com.example.todoapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.room.entity.ToDoData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface ToDoDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveData(todoData: List<ToDoData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveData(todoData: ToDoData)

    @Update
    suspend fun updateData(todoData: ToDoData)

    @Delete
    suspend fun deleteData(todoData: ToDoData)

    @Query("SELECT COUNT(*) FROM  todo_data_table")
    fun observeToDoDataCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM  todo_data_table")
    fun getToDoDataCount(): Int

    @Query("SELECT COUNT(*) FROM  todo_data_table WHERE userId LIKE :userid AND completed LIKE :isCompleted")
    fun getToDoCompetedCountByUser(userid:Int,isCompleted:Boolean): Int

    @Query("SELECT * FROM todo_data_table ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getAllToDoList(limit: Int, offset: Int): List<ToDoData>

    @Query("SELECT * FROM todo_data_table WHERE completed LIKE :isCompleted")
    fun getToDoListByCompletedStatus(isCompleted:Boolean): Flow<List<ToDoData>>

    @Query("SELECT DISTINCT userId FROM todo_data_table")
    fun getUserIdList(): List<Int>

    @Query("SELECT * FROM todo_data_table WHERE userId LIKE :userId")
    fun getToDosByUserId(userId:Int): List<ToDoData>

    @Query("DELETE FROM todo_data_table")
    fun deleteStories(): Int

}