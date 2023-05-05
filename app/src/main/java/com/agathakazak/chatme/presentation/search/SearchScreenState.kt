package com.agathakazak.chatme.presentation.search

import com.agathakazak.chatme.domain.entity.User

sealed class SearchScreenState{
    object Initial: SearchScreenState()
    data class Contacts(
        val contacts: List<User>
    ): SearchScreenState()
}
