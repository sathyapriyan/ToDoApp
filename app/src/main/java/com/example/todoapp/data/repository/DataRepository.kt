package com.example.todoapp.data.repository

import com.example.todoapp.core.util.toToDoDBData
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

    fun getCompliedToDo(): Flow<Result<List<ToDoData>>> {



        /*
                if (CommonUtil.hasInternetConnection(context = context)){
                    saveNewStories()
                }
        */


        return dataDao
            .getToDoListByCompletedStatus(isCompleted = true)
            .onEach {
                if (it.isEmpty()) {
                    saveNewStories()
                }
            }
            .map {

                Result.success(it)

            }
            .catch {
                emit(Result.failure(it))
            }

    }
    fun getInCompliedToDo(): Flow<Result<List<ToDoData>>> {


        /*
                if (CommonUtil.hasInternetConnection(context = context)){
                    saveNewStories()
                }
        */


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

    fun getToDosByUserId(userId: Int): List<ToDoData> = dataDao.getToDosByUserId(userId = userId)

    fun getCompliedToDoByUserId(userid:Int,isCompleted:Boolean): Int = dataDao.getToDoCompetedCountByUser(
        userid = userid,
        isCompleted = isCompleted
    )

    private suspend fun saveNewStories() {

        dataApi.limitAndSkipToDos(limit = 150, skip = 0).also { allStories ->

            println("ToDo Test allStories size  --> ${allStories.body()?.todos?.size}")

            if (dataDao.getToDoDataCount() == -1 || dataDao.getToDoDataCount() == 0) {

                allStories.body()?.todos?.let { it -> dataDao.saveData(todoData = it.map { it.toToDoDBData() }) }
            } else {

                val deleteResponse = dataDao.deleteStories()

                if (deleteResponse != 0) {

                    allStories.body()?.todos?.let { it -> dataDao.saveData(todoData = it.map { it.toToDoDBData() }) }

                }

            }

        }

    }


}