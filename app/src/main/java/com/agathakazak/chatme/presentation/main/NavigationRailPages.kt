package com.agathakazak.chatme.presentation.main

import com.agathakazak.chatme.R
import com.agathakazak.chatme.navigation.Screen

enum class NavigationRailPages(val title: String, val iconId: Int, val screen: Screen) {
    CHATS("chats", R.drawable.message, Screen.ChatsGraph),
    SEARCH("search", R.drawable.search, Screen.Search),
    SETTINGS("settings", R.drawable.setting, Screen.Settings)
}