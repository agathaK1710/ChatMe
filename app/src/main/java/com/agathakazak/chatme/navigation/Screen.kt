package com.agathakazak.chatme.navigation

sealed class Screen(val route: String) {
    object Login: Screen(ROUTE_LOGIN)
    object Registration: Screen(ROUTE_REGISTRATION)
    object Chats: Screen(ROUTE_CHATS)
    object Search: Screen(ROUTE_SEARCH)
    object Settings: Screen(ROUTE_SETTINGS)
    companion object{
        private const val ROUTE_LOGIN = "login"
        private const val ROUTE_REGISTRATION = "registration"
        private const val ROUTE_CHATS = "chats"
        private const val ROUTE_SETTINGS = "settings"
        private const val ROUTE_SEARCH = "search"
    }
}
