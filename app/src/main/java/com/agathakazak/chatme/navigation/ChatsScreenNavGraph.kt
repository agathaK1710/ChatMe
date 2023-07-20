package com.agathakazak.chatme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.agathakazak.chatme.navigation.Screen.Companion.KEY_CHAT_ID

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
                navArgument(KEY_CHAT_ID){
                    type = NavType.IntType
                }
            ),
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/$KEY_CHAT_ID={$KEY_CHAT_ID}" })
        ){
            val chatId = it.arguments?.getInt(KEY_CHAT_ID) ?: 0
            chatScreenContext(chatId)
        }
    }
}