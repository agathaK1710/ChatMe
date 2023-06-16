package com.agathakazak.chatme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.agathakazak.chatme.navigation.Screen.Companion.KEY_RECIPIENT_ID

fun NavGraphBuilder.chatsScreenNavGraph(
    chatsScreenContext: @Composable () -> Unit,
    chatScreenContext: @Composable (recipientId: Int) -> Unit
) {
    navigation(
        startDestination = Screen.Chats.route,
        route = Screen.ChatsGraph.route
    ) {
        val uri = "chatme://${Screen.ChatsGraph.route}"
        composable(Screen.Chats.route){
            chatsScreenContext()
        }
        composable(
            route = Screen.Chat.route,
            arguments = listOf(
                navArgument(KEY_RECIPIENT_ID){
                    type = NavType.IntType
                }
            ),
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/$KEY_RECIPIENT_ID={$KEY_RECIPIENT_ID}" })
        ){
            val recipientId = it.arguments?.getInt(KEY_RECIPIENT_ID) ?: 0
            chatScreenContext(recipientId)
        }
    }
}