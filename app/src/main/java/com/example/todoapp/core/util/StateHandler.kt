package com.example.todoapp.core.util

///////////////////////////////////////////////////////////////////////////////////////////////

// Created by Srinivasan Jayakumar on 30.December.2022:10:18

///////////////////////////////////////////////////////////////////////////////////////////////

sealed class StateHandler<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T): StateHandler<T>(data)
    class Error<T>(message: String?, data: T? = null): StateHandler<T>(data,message)
    class Loading<T>: StateHandler<T>()

}
