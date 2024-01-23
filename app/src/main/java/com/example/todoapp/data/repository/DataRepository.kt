package com.example.todoapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.todoapp.core.paging.MainPagingSource
import com.example.todoapp.core.paging.ToDoPagingSource
import com.example.todoapp.core.util.toToDoDBData
import com.example.todoapp.data.model.AddToDo
import com.example.todoapp.data.model.UpdateToDo
import com.example.todoapp.data.remote.ApiDataSource
import com.example.todoapp.data.room.dao.ToDoDataDao
import com.example.todoapp.data.room.entity.ToDoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val dataApi: ApiDataSource,
    private val dataDao: ToDoDataDao
) {

    fun getCompliedToDo(inNetwork:Boolean): Flow<Result<List<ToDoData>>> {
        println("ToDo Test 1  --> ")

        if(inNetwork){
            return dataDao
                .getToDoListByCompletedStatus(isCompleted = true)
                .onEach {
                    if (it.isEmpty()) {
                        saveNewToDo()
                    }
                }
                .map {
                    Result.success(it)

                }
                .catch {
                    emit(Result.failure(it))
                }

        }else{
            return dataDao
                .getToDoListByCompletedStatus(isCompleted = true)
                .map {
                    Result.success(it)
                }
                .catch {
                    emit(Result.failure(it))
                }

        }



    }
    fun getInCompliedToDo(): Flow<Result<List<ToDoData>>> {


        return dataDao
            .getToDoListByCompletedStatus(isCompleted = false)
            .map {
                Result.success(it)
            }
            .catch {
                emit(Result.failure(it))
            }

    }

    fun observeCount(): Flow<Int> = dataDao.observeToDoDataCount()
    fun getUserIdList(): List<Int> = dataDao.getUserIdList()

    suspend fun getToDosByUserId(inNetwork:Boolean = false,userId: Int): List<ToDoData>? {

        return if(inNetwork){
            dataApi.getAllToDosByUserId(userId = userId).body()?.todos?.map {
                ToDoData(
                    serialNumber = 0,
                    id = it.id,
                    todo = it.todo,
                    completed = it.completed,
                    userId = it.userId,
                    isDeleted = false,
                    deletedOn = ""
                )
            }
        } else dataDao.getToDosByUserId(userId = userId)
    }


    private suspend fun saveNewToDo() {

        dataApi.getAllToDos().also { allStories ->

            println("ToDo Test 3  --> ${allStories.body()?.todos?.size}")

            if (dataDao.isEmpty()|| dataDao.getToDoDataCount() == 0) {

                allStories.body()?.todos?.let { it -> dataDao.saveData(todoData = it.map { it.toToDoDBData() }) }
            } else {

                val deleteResponse = dataDao.deleteStories()

                if (deleteResponse != 0) {

                    allStories.body()?.todos?.let { it -> dataDao.saveData(todoData = it.map { it.toToDoDBData() }) }

                }

            }

        }

    }

    suspend fun saveToDo(inNetwork:Boolean,todo:ToDoData) {
        if(inNetwork){
            val response = dataApi.addToDo(todo = AddToDo(todo = todo.todo, completed = todo.completed, userId = todo.userId))
            dataDao.saveData(todoData = todo.copy(id = response.body()?.id ?: todo.id))
        }else{
            dataDao.saveData(todoData = todo)
        }
    }
    suspend fun updateToDo(inNetwork:Boolean,todo:ToDoData) {
        if(inNetwork){
            val response = dataApi.updateToDo(todo = UpdateToDo(completed = todo.completed))
            dataDao.updateData(todoData = todo.copy(completed  = response.body()?.completed ?: false))
        }else{
            dataDao.updateData(todoData = todo)
        }
    }
    suspend fun deleteToDo(inNetwork:Boolean,todo:ToDoData) {
        if(inNetwork){
            val response = dataApi.deleteToDo(id = todo.id)
            dataDao.deleteData(todoData = todo.copy(isDeleted  = response.body()?.isDeleted ?: false,deletedOn =  response.body()?.deletedOn ?: ""))
        }else{
            dataDao.deleteData(todoData = todo)
        }
    }

    fun getAllToDoData() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            ToDoPagingSource(dataApi)
        }
    ).flow

    fun getAllToDoDataLocal() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            MainPagingSource(dataDao)
        }
    ).flow
}