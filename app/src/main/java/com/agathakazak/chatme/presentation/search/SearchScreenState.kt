package com.agathakazak.chatme.presentation.search

sealed class SearchScreenState{
    object Initial: SearchScreenState()
    data class Contacts(
        val contacts: List<Contact>
    ): SearchScreenState()
}
