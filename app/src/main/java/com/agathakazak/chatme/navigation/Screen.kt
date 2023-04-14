package com.agathakazak.chatme.navigation

sealed class Screen(val route: String) {
    object Login: Screen(ROUTE_LOGIN)
    object Registration: Screen(ROUTE_REGISTRATION)

    companion object{
        private const val ROUTE_LOGIN = "login"
        private const val ROUTE_REGISTRATION = "registration"
    }
}
