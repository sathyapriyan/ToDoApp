package com.example.todoapp.core.navigation

import com.example.todoapp.core.util.ScreenRoot.MAIN_ROOT


sealed class Screen(
    val root:String
){
    object Main: Screen(root = MAIN_ROOT)
}
