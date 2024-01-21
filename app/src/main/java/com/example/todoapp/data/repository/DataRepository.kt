package com.example.todoapp.data.repository

import com.example.todoapp.core.util.StateHandler
import com.example.todoapp.data.remote.ApiDataSource
import com.example.todoapp.data.room.dao.ToDoDataDao
import java.io.IOException
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val dataApi: ApiDataSource,
    private val dataDao: ToDoDataDao,

) {
    suspend fun getAllToDos(
    ): StateHandler<Boolean> {
        try {
            val response = dataApi.getAllToDos()
            return if (response.isSuccessful) {
                when(response.code()) {
                    200 -> {
                        response.body()?.let { binderResponse ->
                            println("Master Binder Response --> $binderResponse")
                        }
                        StateHandler.Success(data = true)
                    }
                    else -> StateHandler.Error(data = false, message = response.errorBody()?.string())
                }
            } else {
                StateHandler.Error(message = response.raw().toString())
            }
        } catch (exception: IOException) {
            return StateHandler.Error(message = exception.localizedMessage)
        }
    }
    suspend fun getSingleToDo(id:Int
    ): StateHandler<Boolean> {
        try {
            val response = dataApi.getSingleToDo(id = id)
            return if (response.isSuccessful) {
                when(response.code()) {
                    200 -> {
                        response.body()?.let { binderResponse ->
                            println("Master Binder Response --> $binderResponse")
                        }
                        StateHandler.Success(data = true)
                    }
                    else -> StateHandler.Error(data = false, message = response.errorBody()?.string())
                }
            } else {
                StateHandler.Error(message = response.raw().toString())
            }
        } catch (exception: IOException) {
            return StateHandler.Error(message = exception.localizedMessage)
        }
    }

}