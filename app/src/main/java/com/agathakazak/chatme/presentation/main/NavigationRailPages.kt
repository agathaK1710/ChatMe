package com.agathakazak.chatme.presentation.main

import com.agathakazak.chatme.R

enum class NavigationRailPages(val title: String, val iconId: Int, val route: String) {
    CHATS("chats", R.drawable.message, "chats"),
    SEARCH("search", R.drawable.search, "search"),
    SETTINGS("settings", R.drawable.setting, "settings")
}