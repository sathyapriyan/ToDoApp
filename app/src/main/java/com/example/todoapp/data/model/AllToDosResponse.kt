package com.example.todoapp.data.model

data class AllToDosResponse(
    val todos: List<ToDos>
)
data class ToDos(
    val id:Int,
    val todo:String,
    val completed:Boolean,
    val userId:Int,
)
