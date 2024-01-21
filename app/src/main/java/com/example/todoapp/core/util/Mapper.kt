package com.example.todoapp.core.util

import com.example.todoapp.data.model.AllToDosResponse
import com.example.todoapp.data.model.ToDos
import com.example.todoapp.data.room.entity.ToDoData




fun ToDos.toToDoDBData(): ToDoData {

    return ToDoData(
        serialNumber = 0,
        id =id,
    todo = todo,
    completed = completed,
    userId = userId,
    isDeleted = false,
    deletedOn = ""
    )

}
