package com.agathakazak.chatme.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agathakazak.chatme.domain.Chat
import com.agathakazak.chatme.domain.User
import com.agathakazak.chatme.navigation.AppNavGraph
import com.agathakazak.chatme.navigation.Screen
import com.agathakazak.chatme.navigation.rememberNavigationState
import com.agathakazak.chatme.presentation.chats.ChatScreen
import com.agathakazak.chatme.presentation.mainContent.ContentScreen
import com.agathakazak.chatme.presentation.mainContent.NavigationRailPages
import com.agathakazak.chatme.presentation.login.LoginScreen
import com.agathakazak.chatme.presentation.login.LoginState
import com.agathakazak.chatme.presentation.login.LoginViewModel
import com.agathakazak.chatme.presentation.registration.RegistrationScreen

@Composable
fun MainScreen() {
    val viewModel: LoginViewModel = viewModel()
    val logState = viewModel.loginState.observeAsState(LoginState.Initial)
    val navigationState = rememberNavigationState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AppNavGraph(
            navHostController = navigationState.navHostController,
            logState.value,
            loginScreenContext = {
                LoginScreen(
                    onClickSignUp = {
                        navigationState.navigateTo(Screen.Registration.route)
                    },
                    navigateToMainScreen = {
                        navigationState.navigateTo(Screen.Content.route)
                    }
                )
            },
            registrationScreenContext = {
                RegistrationScreen(
                    onClickSignIn = {
                        navigationState.navigateTo(Screen.Login.route)
                    }
                )
            },
            contentScreenContext = {
               ContentScreen(navController = navigationState.navHostController)
            },
            chatsScreenContext = {
                val chats = mutableListOf<Chat>()
                repeat(15){
                    val user = User("Agatha $it", "Kazak", "+375444977921", "agatha.kazak@gmail.com $it", "vsd", "https://sun9-31.userapi.com/impg/pauakvL_KO6RwebwYJhMH6fWi7FCSB3FSHrYLQ/tvPWQJmREao.jpg?size=1440x2160&quality=95&sign=35628ec8352dba59f2a42252c62f49a7&type=album")
                    chats.add(Chat(user, "message $it"))
                }
                ChatScreen(chats)
            }
        )
    }
}