package com.example.todoapp.core.navigation

import com.example.todoapp.core.util.ScreenRoot.MAIN_ROOT
import com.example.todoapp.core.util.ScreenRoot.USER_ROOT


const val USER_ID="user_id"
sealed class Screen(
    val root:String
){
    data object Main: Screen(root = MAIN_ROOT)

    data object User :
        Screen(root = "${USER_ROOT}/{$USER_ID}") {

        fun passUserId(userId: Int): String {
            return this.root.replace(
                oldValue = "{$USER_ID}",
                newValue = userId.toString()
            )
        }

    }
}
