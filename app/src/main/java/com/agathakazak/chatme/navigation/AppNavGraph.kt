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
    contentScreenContext: @Composable () -> Unit,
    chatsScreenContext: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = if (logState is LoginState.IsLogged)
            Screen.Content.route else Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            loginScreenContext()
        }

        composable(Screen.Registration.route) {
            registrationScreenContext()
        }

        composable(Screen.Content.route) {
            contentScreenContext()
        }

        composable(Screen.Chats.route) {
            chatsScreenContext()
        }
    }
}