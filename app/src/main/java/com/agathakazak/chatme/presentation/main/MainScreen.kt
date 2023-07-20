package com.agathakazak.chatme.presentation.main

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.agathakazak.chatme.domain.entity.ChatDetail
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.navigation.AppNavGraph
import com.agathakazak.chatme.navigation.NavigationState
import com.agathakazak.chatme.navigation.Screen
import com.agathakazak.chatme.navigation.rememberNavigationState
import com.agathakazak.chatme.presentation.ViewModelFactory
import com.agathakazak.chatme.presentation.chats.ChatScreen
import com.agathakazak.chatme.presentation.chats.ChatTopBar
import com.agathakazak.chatme.presentation.login.LoginScreen
import com.agathakazak.chatme.presentation.login.LoginState
import com.agathakazak.chatme.presentation.login.LoginViewModel
import com.agathakazak.chatme.presentation.messages.MessagesScreen
import com.agathakazak.chatme.presentation.messages.MessagesTopBar
import com.agathakazak.chatme.presentation.registration.RegistrationScreen
import com.agathakazak.chatme.presentation.registration.RegistrationState
import com.agathakazak.chatme.presentation.registration.RegistrationViewModel
import com.agathakazak.chatme.presentation.search.SearchScreen
import com.agathakazak.chatme.presentation.search.SearchTopBar

@Composable
fun MainScreen(viewModelFactory: ViewModelFactory) {
    val loginViewModel: LoginViewModel = viewModel(factory = viewModelFactory)
    val logState = loginViewModel.loginState.observeAsState(LoginState.Initial)
    var menuState by rememberSaveable { mutableStateOf((logState.value == LoginState.IsLogged)) }
    val navigationState = rememberNavigationState()
    val focusRequester = remember { FocusRequester() }
    val currentDestination =
        navigationState.navHostController.currentBackStackEntryAsState().value?.destination
    val items = listOf(
        NavigationRailPages.CHATS, NavigationRailPages.SEARCH, NavigationRailPages.SETTINGS
    )
    var search by rememberSaveable { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }
    var chat by rememberSaveable { mutableStateOf<ChatDetail?>(null) }
    var longClick by rememberSaveable { mutableStateOf(false) }
    val size by animateDpAsState(
        targetValue = if (menuState) 72.dp else 0.dp,
    )
    val alpha by animateFloatAsState(targetValue = if (menuState) 1f else 0f)
    Scaffold(
        topBar = {
            if (logState.value == LoginState.IsLogged) {
                MainTopBar(
                    currentDestination,
                    menuClick = {
                        menuState = !menuState
                    },
                    search,
                    searchText,
                    changeSearchState = {
                        search = it
                    },
                    changeSearchText = {
                        searchText = it
                    },
                    focusRequester,
                    navigationState,
                    chat,
                    longClick
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = size)
            ) {
                NavigationGraph(
                    navigationState,
                    loginViewModel,
                    viewModelFactory,
                    logState,
                    changeMenuState = {
                        menuState = false
                    },
                    setChat = {
                        chat = it
                    },
                    setOnLongClick = {
                        longClick = it
                    }
                )
            }
            MenuBar(navigationState, items, size, alpha) { page ->
                search = false
                searchText = ""
                navigationState.navHostController.navigate(page.screen.route)
            }
        }
    }
}


@Composable
private fun MainTopBar(
    currentDestination: NavDestination?,
    menuClick: () -> Unit,
    search: Boolean,
    searchText: String,
    changeSearchState: (Boolean) -> Unit,
    changeSearchText: (String) -> Unit,
    focusRequester: FocusRequester,
    navigationState: NavigationState,
    chat: ChatDetail?,
    longClick: Boolean
) {
    when (currentDestination?.route) {
        Screen.Search.route -> {
            SearchTopBar(
                searchPressed = search,
                searchText = searchText,
                focusRequester = focusRequester,
                backClick = {
                    changeSearchState(false)
                    changeSearchText("")
                },
                menuClick = { menuClick() },
                searchTextChange = {
                    changeSearchText(it)
                },
                searchClick = { changeSearchState(true) }
            )
        }

        Screen.Chat.route -> {
            if (!longClick) {
                if (chat != null) {
                    MessagesTopBar(chat) {
                        navigationState.navHostController.popBackStack()
                    }
                } else {
                    ChatTopBar {
                        menuClick()
                    }
                }
            }
        }

        else -> {
            ChatTopBar {
                menuClick()
            }
        }
    }
}

@Composable
private fun NavigationGraph(
    navigationState: NavigationState,
    loginViewModel: LoginViewModel,
    viewModelFactory: ViewModelFactory,
    logState: State<LoginState>,
    changeMenuState: () -> Unit,
    setChat: (ChatDetail) -> Unit,
    setOnLongClick: (Boolean) -> Unit
) {
    val registrationViewModel: RegistrationViewModel = viewModel(factory = viewModelFactory)
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
                },
                viewModelFactory
            )
        },
        registrationScreenContext = {
            RegistrationScreen(
                onClickSignIn = {
                    registrationViewModel.changeRegistrationState(RegistrationState.IsRegistered)
                    navigationState.navigateTo(Screen.Login.route)
                },
                viewModelFactory
            )
        },
        chatsScreenContext = {
            ChatScreen(viewModelFactory) {
                navigationState.navigateToChat(it)
            }
        },
        chatScreenContext = {
            changeMenuState()
            MessagesScreen(
                it,
                setChat = { chat ->
                    setChat(
                        chat
                    )
                },
                setOnLongClick = {
                    setOnLongClick(it)
                }
            )
        },
        searchScreenContext = {
            SearchScreen(viewModelFactory)
        },
        settingsScreenContext = {
            Text(
                text = "Settings page",
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.onSecondary
            )
        }
    )
}

@Composable
private fun MenuBar(
    navigationState: NavigationState,
    items: List<NavigationRailPages>,
    width: Dp = 72.dp,
    alpha: Float = 1f,
    onClick: (page: NavigationRailPages) -> Unit
) {
    NavigationRail(
        modifier = Modifier
            .width(width)
            .alpha(alpha)
    ) {
        val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
        items.forEach { page ->
            val selected = navBackStackEntry?.destination?.hierarchy?.any {
                it.route == page.screen.route
            } ?: false
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
                    onClick(page)
                }
            )
        }
    }
}
