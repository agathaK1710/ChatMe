package com.agathakazak.chatme.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.entity.SimpleResponse
import com.agathakazak.chatme.domain.usecases.RegisterUserUseCase
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
): ViewModel() {
    private val _registrationState = MutableLiveData<RegistrationState>(RegistrationState.Initial)
    val registrationState: LiveData<RegistrationState> = _registrationState

    fun registerUser(user: User) {
        viewModelScope.launch {
            try {
                _registrationState.value = RegistrationState.Loading
                delay(1000)
                registerUserUseCase(user)
                _registrationState.value = RegistrationState.IsRegistered
            } catch (e: HttpException) {
                val responseString = e.response()?.errorBody()?.string()
                val gson = Gson()
                val response = gson.fromJson(responseString, SimpleResponse::class.java)
                _registrationState.value =
                    RegistrationState.IsRegistrationError(response as SimpleResponse<String>)
            }
        }
    }

    fun changeRegistrationState(state: RegistrationState){
        _registrationState.value = state
    }

}