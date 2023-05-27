package com.agathakazak.chatme.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
        composable(Screen.Chats.route){
            chatsScreenContext()
        }
        composable(Screen.Chat.route){
            val recipientId = it.arguments?.getString(KEY_RECIPIENT_ID)?.toInt() ?: 0
            chatScreenContext(recipientId)
        }
    }
}