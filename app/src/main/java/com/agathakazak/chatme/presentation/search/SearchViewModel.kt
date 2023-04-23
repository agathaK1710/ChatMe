package com.agathakazak.chatme.presentation.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SearchViewModel() : ViewModel() {
    private val userRepository = UserRepository()

    private val _searchScreenState = MutableLiveData<SearchScreenState>(SearchScreenState.Initial)
    val searchScreenState: LiveData<SearchScreenState> = _searchScreenState

    fun getRegisteredContacts(allContacts: List<Contact>) {
        val registeredContacts = mutableListOf<Contact>()
        viewModelScope.launch {
            allContacts.forEach {
                try {
                    val phoneNumber = it.number.filter { !it.isWhitespace() }
                    val user = userRepository.getUserByPhoneNumber(phoneNumber).data
                    registeredContacts.add(
                        Contact(
                            name = "${user.firstName} ${user.lastName}",
                            user.phoneNumber
                        )
                    )
                } catch (_: HttpException) {
                }
            }
            _searchScreenState.value = SearchScreenState.Contacts(registeredContacts)
        }
    }
}