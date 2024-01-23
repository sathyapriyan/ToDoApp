package com.example.todoapp.data.model

data class LimitAndSkipToDosResponse(
    val todos: List<ToDos>,
    val total:Int,
    val skip:Int,
    val limit:Int,
)

