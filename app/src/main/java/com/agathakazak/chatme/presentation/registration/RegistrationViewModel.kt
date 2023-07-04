package com.agathakazak.chatme.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.domain.entity.UserRegister
import com.agathakazak.chatme.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
): ViewModel() {
    private val _registrationState = MutableLiveData<RegistrationState>(RegistrationState.Initial)
    val registrationState: LiveData<RegistrationState> = _registrationState

    fun registerUser(user: UserRegister) {
        viewModelScope.launch {
            try {
                _registrationState.value = RegistrationState.Loading
                delay(1000)
                registerUserUseCase(user)
                _registrationState.value = RegistrationState.IsRegistered
            } catch (e: HttpException) {
                val responseString = e.response()?.errorBody()?.string()
                _registrationState.value =
                    responseString?.let { RegistrationState.IsRegistrationError(it) }
            }
        }
    }

    fun changeRegistrationState(state: RegistrationState){
        _registrationState.value = state
    }

}