package com.example.todoapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoapp.presentation.screen.main.MainView
import com.example.todoapp.presentation.screen.user.UserView

@Composable
fun RootNavigation(
    navHostController: NavHostController) {

    NavHost(navController = navHostController,
        startDestination = Screen.Main.root) {

        composable(route= Screen.Main.root){

            MainView(navHostController= navHostController)
        }
        composable(
            route = Screen.User.root,
            arguments = listOf(navArgument(
                USER_ID
            ) {
                type = NavType.StringType
            })
        ) {

            println("Record Id Passed from Records Screen --> ${it.arguments?.getString(USER_ID)}")

            UserView(
                navHostController = navHostController,
                userId = it.arguments?.getString(USER_ID)!!
            )
        }
    }
}