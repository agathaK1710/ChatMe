package com.agathakazak.chatme.presentation.login

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.agathakazak.chatme.data.repository.UserRepository
import com.agathakazak.chatme.domain.UserLogin
import com.agathakazak.chatme.domain.UserResponse
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException


class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository()
    private val sharedPreferences = application.getSharedPreferences("chatMe", Context.MODE_PRIVATE)

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
                val response: UserResponse<String> = userRepository.loginUser(userLogin)
                val token = if (response.success) response.data else null
                saveToken(token)
                _loginState.value = LoginState.IsLogged
            } catch (e: HttpException) {
                val responseString = e.response()?.errorBody()?.string()
                val gson = Gson()
                val response = gson.fromJson(responseString, UserResponse::class.java)
                _loginState.value = LoginState.IsLoggingError(response as UserResponse<String>)
            }

        }
    }

    fun changeLoginState(state: LoginState){
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