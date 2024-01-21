package com.example.todoapp.data.repository

import com.example.todoapp.core.util.StateHandler
import com.example.todoapp.core.util.toToDoDBData
import com.example.todoapp.data.model.AllToDosResponse
import com.example.todoapp.data.model.ToDos
import com.example.todoapp.data.remote.ApiDataSource
import com.example.todoapp.data.room.dao.ToDoDataDao
import com.example.todoapp.data.room.entity.ToDoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val dataApi: ApiDataSource,
    private val dataDao: ToDoDataDao,

) {

    fun getNewStories(storyType: Int = 1,refresh:Boolean=false): Flow<Result<List<ToDoData>>> {

        return dataDao
            .getAllStoriesList()
            .onEach {
                if (it.isEmpty() || refresh) {
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

    fun getArticleItemsCount(): Int = dataDao.getToDoDataCount()

    private suspend fun saveNewStories() {

        dataApi.getAllToDos().also { allStories ->

            if (dataDao.getToDoDataCount() == -1 || dataDao.getToDoDataCount() == 0) {

                allStories.body()?.todos?.let { dataDao.saveData(todoData = it.map { it.toToDoDBData() }) }

            } else {

                val deleteResponse = dataDao.deleteStories()

                if (deleteResponse != 0) {

                    allStories.body()?.todos?.let { dataDao.saveData(todoData = it.map { it.toToDoDBData() }) }

                }

            }

        }

    }


}