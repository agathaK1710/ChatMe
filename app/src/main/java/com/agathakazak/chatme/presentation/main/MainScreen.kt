package com.agathakazak.chatme.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.agathakazak.chatme.R
import com.agathakazak.chatme.domain.Chat
import com.agathakazak.chatme.domain.User
import com.agathakazak.chatme.navigation.AppNavGraph
import com.agathakazak.chatme.navigation.NavigationState
import com.agathakazak.chatme.navigation.Screen
import com.agathakazak.chatme.navigation.rememberNavigationState
import com.agathakazak.chatme.presentation.chats.ChatScreen
import com.agathakazak.chatme.presentation.login.LoginScreen
import com.agathakazak.chatme.presentation.login.LoginState
import com.agathakazak.chatme.presentation.login.LoginViewModel
import com.agathakazak.chatme.presentation.registration.RegistrationScreen
import com.agathakazak.chatme.presentation.registration.RegistrationState
import com.agathakazak.chatme.presentation.registration.RegistrationViewModel
import com.agathakazak.chatme.presentation.search.SearchScreen

@Composable
fun MainScreen() {
    val loginViewModel: LoginViewModel = viewModel()
    val logState = loginViewModel.loginState.observeAsState(LoginState.Initial)
    var menuState by rememberSaveable { mutableStateOf((logState.value == LoginState.IsLogged)) }
    val navigationState = rememberNavigationState()
    val items = listOf(
        NavigationRailPages.CHATS, NavigationRailPages.SEARCH, NavigationRailPages.SETTINGS
    )
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            if (logState.value == LoginState.IsLogged) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 15.dp)
                                .size(30.dp)
                                .clickable {
                                    menuState = !menuState
                                },
                            tint = Color.White
                        )
                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant
                )
            }
        }
    ) { paddingValues ->
        menuState = logState.value == LoginState.IsLogged
        Row(modifier = Modifier.fillMaxSize()) {
            if (menuState) {
                NavigationRail(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    val currentDestination =
                        navigationState.navHostController.currentBackStackEntryAsState().value?.destination
                    items.forEach { page ->
                        val selected = currentDestination?.route?.let { it == page.route } ?: false
                        NavigationRailItem(
                            icon = {
                                Icon(
                                    painterResource(page.iconId),
                                    modifier = Modifier.size(25.dp),
                                    contentDescription = null
                                )
                            },
                            selected = selected,
                            label = {
                                Text(
                                    page.title,
                                    color = if (selected) MaterialTheme.colors.primaryVariant
                                    else MaterialTheme.colors.onSecondary
                                )
                            },
                            selectedContentColor = MaterialTheme.colors.primaryVariant,
                            unselectedContentColor = MaterialTheme.colors.onSecondary,
                            onClick = {
                                navigationState.navHostController.navigate(page.route)
                            }
                        )
                    }
                }
            }
            NavigationGraph(navigationState, loginViewModel, logState)
        }
    }
}

@Composable
private fun NavigationGraph(
    navigationState: NavigationState,
    loginViewModel: LoginViewModel,
    logState: State<LoginState>
) {
    val registrationViewModel: RegistrationViewModel = viewModel()
    AppNavGraph(
        navHostController = navigationState.navHostController,
        logState.value,
        loginScreenContext = {
            LoginScreen(
                onClickSignUp = {
                    navigationState.navigateTo(Screen.Registration.route)
                },
                navigateToMainScreen = {
                    loginViewModel.changeLoginState(LoginState.IsLogged)
                    navigationState.navigateTo(Screen.Chats.route)
                }
            )
        },
        registrationScreenContext = {
            RegistrationScreen(
                onClickSignIn = {
                    registrationViewModel.changeRegistrationState(RegistrationState.IsRegistered)
                    navigationState.navigateTo(Screen.Login.route)
                }
            )
        },
        chatsScreenContext = {
            val chats = mutableListOf<Chat>()
            repeat(15) {
                val user = User(
                    "Agatha $it",
                    "Kazak",
                    "+375444977921",
                    "agatha.kazak@gmail.com $it",
                    "vsd",
                    "https://sun9-31.userapi.com/impg/pauakvL_KO6RwebwYJhMH6fWi7FCSB3FSHrYLQ/tvPWQJmREao.jpg?size=1440x2160&quality=95&sign=35628ec8352dba59f2a42252c62f49a7&type=album"
                )
                chats.add(Chat(user, "message $it"))
            }
            ChatScreen(chats)
        },
        searchScreenContext = {
            SearchScreen()
        },
        settingsScreenContext = {
            Text(text = "Settings page", modifier = Modifier.fillMaxSize())
        }
    )
}