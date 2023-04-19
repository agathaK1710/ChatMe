package com.agathakazak.chatme.presentation.mainContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.agathakazak.chatme.R

@Composable
fun ContentScreen(navController: NavHostController) {
    val items = listOf(
        NavigationRailPages.CHATS, NavigationRailPages.SEARCH, NavigationRailPages.SETTINGS
    )
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(
                    text = stringResource(id = R.string.app_name),
                    color = Color.White
                ) },
                backgroundColor = MaterialTheme.colors.primaryVariant
            )
        }
    ) { paddingValues ->
        NavigationRail(
            modifier = Modifier.padding(paddingValues)
        ) {
            val currentDestination = navController.currentBackStackEntryAsState().value?.destination
            items.forEach { page ->
                NavigationRailItem(
                    icon = {
                        Icon(
                            painterResource(page.iconId),
                            modifier = Modifier.size(25.dp),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primaryVariant
                        )
                    },
                    label = { Text(page.title, color = MaterialTheme.colors.onPrimary) },
                    selected = currentDestination?.route?.let { it == page.route } ?: false,
                    onClick = { navController.navigate(page.route) }
                )
            }
        }
    }
}
