package com.agathakazak.chatme.data.network

import com.agathakazak.chatme.data.model.UserDto
import com.agathakazak.chatme.data.model.UserLoginDto
import com.agathakazak.chatme.data.model.UserResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun registerUser(
        @Body user: UserDto
    ): UserResponseDto<String>

    @POST("login")
    suspend fun loginUser(
        @Body userLogin: UserLoginDto
    ): UserResponseDto<UserDto>

}