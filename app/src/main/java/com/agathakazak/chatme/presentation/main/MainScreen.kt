package com.agathakazak.chatme.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agathakazak.chatme.navigation.AppNavGraph
import com.agathakazak.chatme.navigation.Screen
import com.agathakazak.chatme.navigation.rememberNavigationState

@Composable
fun MainScreen() {
    val viewModel:MainViewModel = viewModel()
    val logState = viewModel.loginState.observeAsState(LoginState.Initial)
    val navigationState = rememberNavigationState()
    Box(modifier = Modifier.fillMaxSize()) {
        AppNavGraph(
            navHostController = navigationState.navHostController,
            logState.value,
            loginScreenContext = {
                LoginScreen(
                    onClickSignUp = {
                        navigationState.navigateTo(Screen.Registration.route)
                    },
                    navigateToChats = {}
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