package com.example.todoapp.data.remote

import com.example.todoapp.data.model.AllToDosResponse
import com.example.todoapp.data.model.DeleteToDo
import com.example.todoapp.data.model.LimitAndSkipToDosResponse
import com.example.todoapp.data.model.ToDos
import com.example.todoapp.data.model.UpdateToDo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiDataSource {

    @GET("todos")
    suspend fun getAllToDos(): Response<AllToDosResponse>

    @GET("todos/{id}")
    suspend fun getSingleToDo(@Path("id") id:Int): Response<ToDos>

    @GET("random")
    suspend fun getRandomToDos(): Response<AllToDosResponse>

    @GET("todos")
    suspend fun limitAndSkipToDos(@Query("limit") limit:Int,@Query("skip") skip:Int): Response<LimitAndSkipToDosResponse>

    @POST("todos/add")
    suspend fun addToDo(
        @Body todo:AllToDosResponse
    ): Response<ToDos>

    @POST("todos")
    suspend fun updateToDo(
        @Body todo: UpdateToDo
    ): Response<ToDos>

    @DELETE("todos")
    suspend fun deleteToDo(@Query("id") id:Int): Response<DeleteToDo>
}