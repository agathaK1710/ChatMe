package com.agathakazak.chatme.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.usecase.GetUserByPhoneNumberUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getUserByPhoneNumberUseCase: GetUserByPhoneNumberUseCase
) : ViewModel() {

    private val _searchScreenState = MutableLiveData<SearchScreenState>(SearchScreenState.Initial)
    val searchScreenState: LiveData<SearchScreenState> = _searchScreenState

    fun getRegisteredContacts(allContacts: List<String>) {
        val registeredUsers = mutableListOf<User>()
        viewModelScope.launch {
            allContacts.forEach {
                try {
                    val phoneNumber = it.filter { !it.isWhitespace() }
                    val user = getUserByPhoneNumberUseCase(phoneNumber)
                    registeredUsers.add(user)
                } catch (_: HttpException) {
                }
            }
            _searchScreenState.value = SearchScreenState.Contacts(registeredUsers)
        }
    }
}