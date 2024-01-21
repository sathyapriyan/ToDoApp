package com.example.todoapp.data.model

data class DeleteToDo(
    val id:Int,
    val todo:String,
    val completed:Boolean,
    val userId:Int,
    val isDeleted:Boolean,
    val deletedOn:String,
)

