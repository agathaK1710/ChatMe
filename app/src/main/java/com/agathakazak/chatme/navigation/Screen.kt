package com.agathakazak.chatme.navigation

sealed class Screen(val route: String) {
    object Login : Screen(ROUTE_LOGIN)
    object Registration : Screen(ROUTE_REGISTRATION)
    object Chats : Screen(ROUTE_CHATS)
    object Chat : Screen(ROUTE_CHAT) {
        private const val ROUTE_FOR_ARGS = "chat"
        fun getRouteWithArguments(recipientId: Int): String {
            return "$ROUTE_FOR_ARGS/$recipientId"
        }
    }

    object ChatsGraph : Screen(ROUTE_CHATS_GRAPH)
    object Search : Screen(ROUTE_SEARCH)
    object Settings : Screen(ROUTE_SETTINGS)
    companion object {
        private const val ROUTE_LOGIN = "login"
        private const val ROUTE_REGISTRATION = "registration"
        const val KEY_RECIPIENT_ID = "recipient_id"
        private const val ROUTE_CHAT = "chat/{$KEY_RECIPIENT_ID}"
        private const val ROUTE_CHATS = "chats"
        private const val ROUTE_CHATS_GRAPH = "chats_graph"
        private const val ROUTE_SETTINGS = "settings"
        private const val ROUTE_SEARCH = "search"
    }
}
