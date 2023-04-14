package com.agathakazak.chatme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    loginScreenContext: @Composable () -> Unit,
    registrationScreenContext: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login.route) {

        composable(Screen.Login.route){
            loginScreenContext()
        }

        composable(Screen.Registration.route){
            registrationScreenContext()
        }
    }
}