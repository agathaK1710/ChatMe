package com.agathakazak.chatme.presentation.login

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.domain.entity.UserLogin
import com.agathakazak.chatme.domain.usecase.LoginUserUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


class LoginViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>(LoginState.Initial)
    val loginState: LiveData<LoginState> = _loginState

    init {
        _loginState.value = if (getToken() != null) LoginState.IsLogged else LoginState.NotLogged
    }


    fun loginUser(userLogin: UserLogin) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginState.Loading
                delay(1000)
                val token = loginUserUseCase(userLogin)
                saveToken(token.data)
                _loginState.value = LoginState.IsLogged
            } catch (e: HttpException) {
                val responseString = e.response()?.errorBody()?.string()
                _loginState.value = responseString?.let { LoginState.IsLoggingError(it) }
            }

        }
    }

    fun changeLoginState(state: LoginState) {
        _loginState.value = state
    }

    private fun saveToken(token: String?) {
        sharedPreferences.edit().putString(TOKEN, token).apply()
    }

    private fun getToken(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    companion object {
        private const val TOKEN = "token"
    }
}