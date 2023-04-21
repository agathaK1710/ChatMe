package com.agathakazak.chatme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.agathakazak.chatme.presentation.login.LoginState


@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    logState: LoginState,
    loginScreenContext: @Composable () -> Unit,
    registrationScreenContext: @Composable () -> Unit,
    chatsScreenContext: @Composable () -> Unit,
    searchScreenContext: @Composable () -> Unit,
    settingsScreenContext: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = if (logState is LoginState.IsLogged)
            Screen.Chats.route else Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            loginScreenContext()
        }
        composable(Screen.Registration.route) {
            registrationScreenContext()
        }
        composable(Screen.Chats.route){
            chatsScreenContext()
        }
        composable(Screen.Search.route) {
            searchScreenContext()
        }
        composable(Screen.Settings.route) {
            settingsScreenContext()
        }
    }
}