package com.example.todoapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.core.util.constants.TABLE_TODO_DATA

@Entity(tableName = TABLE_TODO_DATA)
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    val serialNumber: Int,
    val id:Int,
    val todo:String,
    val completed:Boolean,
    val userId:Int,
    val isDeleted:Boolean,
    val deletedOn:String
)
