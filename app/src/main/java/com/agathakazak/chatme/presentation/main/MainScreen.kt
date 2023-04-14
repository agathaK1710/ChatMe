package com.agathakazak.chatme.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.agathakazak.chatme.navigation.AppNavGraph
import com.agathakazak.chatme.navigation.Screen
import com.agathakazak.chatme.navigation.rememberNavigationState

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()
    Box(modifier = Modifier.fillMaxSize()) {
        AppNavGraph(
            navHostController = navigationState.navHostController,
            loginScreenContext = {
                LoginScreen(
                    onClickSignUp = {
                        navigationState.navigateTo(Screen.Registration.route)
                    }
                )
            },
            registrationScreenContext = {
                RegistrationScreen(
                    onClickSignIn = {
                        navigationState.navigateTo(Screen.Login.route)
                    }
                )
            }
        )
    }
}