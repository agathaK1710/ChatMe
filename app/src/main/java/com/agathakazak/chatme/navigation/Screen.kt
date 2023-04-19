package com.agathakazak.chatme.navigation

sealed class Screen(val route: String) {
    object Login: Screen(ROUTE_LOGIN)
    object Registration: Screen(ROUTE_REGISTRATION)
    object Content: Screen(ROUTE_CONTENT)
    object Chats: Screen(ROUTE_CHATS)

    companion object{
        private const val ROUTE_LOGIN = "login"
        private const val ROUTE_REGISTRATION = "registration"
        private const val ROUTE_CONTENT = "content"
        private const val ROUTE_CHATS = "chats"
    }
}
