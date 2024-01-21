package com.example.todoapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.presentation.screen.main.MainView

@Composable
fun RootNavigation(
    navHostController: NavHostController) {

    NavHost(navController = navHostController,
        startDestination = Screen.Main.root) {

        composable(route= Screen.Main.root){

            MainView(navHostController= navHostController)
        }
    }
}