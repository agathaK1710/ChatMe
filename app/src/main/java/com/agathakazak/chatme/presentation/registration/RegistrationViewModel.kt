package com.agathakazak.chatme.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.data.repository.UserRepository
import com.agathakazak.chatme.domain.User
import com.agathakazak.chatme.domain.UserResponse
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegistrationViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _registrationState = MutableLiveData<RegistrationState>(RegistrationState.Initial)
    val registrationState: LiveData<RegistrationState> = _registrationState

    fun registerUser(user: User) {
        viewModelScope.launch {
            try {
                _registrationState.value = RegistrationState.Loading
                delay(1000)
                userRepository.registerUser(user)
                _registrationState.value = RegistrationState.IsRegistered
            } catch (e: HttpException) {
                val responseString = e.response()?.errorBody()?.string()
                val gson = Gson()
                val response = gson.fromJson(responseString, UserResponse::class.java)
                _registrationState.value =
                    RegistrationState.IsRegistrationError(response as UserResponse<String>)
            }
        }
    }

}