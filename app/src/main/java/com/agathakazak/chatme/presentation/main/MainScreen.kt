package com.agathakazak.chatme.presentation.main

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.agathakazak.chatme.R
import com.agathakazak.chatme.navigation.AppNavGraph
import com.agathakazak.chatme.navigation.NavigationState
import com.agathakazak.chatme.navigation.Screen
import com.agathakazak.chatme.navigation.rememberNavigationState
import com.agathakazak.chatme.presentation.ViewModelFactory
import com.agathakazak.chatme.presentation.messages.MessagesScreen
import com.agathakazak.chatme.presentation.chats.ChatScreen
import com.agathakazak.chatme.presentation.login.LoginScreen
import com.agathakazak.chatme.presentation.login.LoginState
import com.agathakazak.chatme.presentation.login.LoginViewModel
import com.agathakazak.chatme.presentation.registration.RegistrationScreen
import com.agathakazak.chatme.presentation.registration.RegistrationState
import com.agathakazak.chatme.presentation.registration.RegistrationViewModel
import com.agathakazak.chatme.presentation.search.SearchScreen

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
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            if (logState.value == LoginState.IsLogged) {
                MainTopBar(
                    currentDestination,
                    search,
                    searchText,
                    focusRequester,
                    backClick = {
                        search = false
                        searchText = ""
                    },
                    menuClick = {
                        menuState = !menuState
                    },
                    searchTextChange = {
                        searchText = it
                    },
                    searchClick = {
                        search = true
                    },
                    chatBack = {
                        navigationState.navHostController.popBackStack()
                        menuState = !menuState
                    }
                )
            }
        }
    ) { paddingValues ->
        val size by animateDpAsState(
            targetValue = if (menuState) 72.dp else 0.dp,
        )
        val alfa by animateFloatAsState(targetValue = if (menuState) 1f else 0f)
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = size)
            ) {
                NavigationGraph(navigationState, loginViewModel, viewModelFactory, logState) {
                    menuState = false
                }
            }
            MenuBar(paddingValues, navigationState, items, size, alfa) { page ->
                search = false
                searchText = ""
                navigationState.navHostController.navigate(page.screen.route)
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
    changeMenuState: () -> Unit
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
                changeMenuState()
            }
        },
        chatScreenContext = {
            MessagesScreen(it)
        },
        searchScreenContext = {
            SearchScreen(viewModelFactory)
        },
        settingsScreenContext = {
            Text(text = "Settings page", modifier = Modifier.fillMaxSize())
        }
    )
}

@Composable
private fun MenuBar(
    paddingValues: PaddingValues,
    navigationState: NavigationState,
    items: List<NavigationRailPages>,
    width: Dp = 72.dp,
    alpha: Float = 1f,
    onClick: (page: NavigationRailPages) -> Unit
) {
    NavigationRail(
        modifier = Modifier
            .padding(paddingValues)
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

@Composable
private fun MainTopBar(
    currentDestination: NavDestination?,
    search: Boolean,
    searchText: String,
    focusRequester: FocusRequester,
    backClick: () -> Unit,
    chatBack: () -> Unit,
    menuClick: () -> Unit,
    searchTextChange: (String) -> Unit,
    searchClick: (Boolean) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White
            )
        },
        navigationIcon = {
            if (search || currentDestination?.route == Screen.Chat.route) {
                IconButton(onClick = {
                    if (currentDestination?.route == Screen.Chat.route) {
                        chatBack()
                    } else {
                        backClick()
                    }
                }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .size(25.dp),
                        tint = Color.White
                    )
                }
            } else {
                IconButton(onClick = {
                    menuClick()
                }) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .size(30.dp),
                        tint = Color.White
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        actions = {
            if (currentDestination?.route == Screen.Search.route) {
                if (search) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = searchText,
                        onValueChange = {
                            searchTextChange(it)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = MaterialTheme.colors.primaryVariant,
                            textColor = Color.White,
                            cursorColor = Color.White
                        ),
                        placeholder = {
                            Text(text = "Search contact", color = Color.White)
                        },
                        maxLines = 1,
                        singleLine = true,
                        trailingIcon = {
                            if (searchText.isNotBlank()) {
                                IconButton(onClick = {
                                    searchTextChange("")
                                }) {
                                    Icon(
                                        Icons.Filled.Close,
                                        null,
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    )
                    LaunchedEffect(search) {
                        if (search) focusRequester.requestFocus()
                    }
                }
                IconButton(onClick = {
                    searchClick(true)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = Color.White
                    )
                }
            }
        }
    )
}
