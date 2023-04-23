package com.agathakazak.chatme.data.network

import com.agathakazak.chatme.data.model.UserDto
import com.agathakazak.chatme.data.model.UserLoginDto
import com.agathakazak.chatme.data.model.UserResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    suspend fun registerUser(
        @Body user: UserDto
    ): UserResponseDto<String>

    @POST("login")
    suspend fun loginUser(
        @Body userLogin: UserLoginDto
    ): UserResponseDto<String>

    @GET("user")
    suspend fun getUserByPhone(
        @Query(QUERY_PHONE_NUMBER, encoded = true) phoneNumber: String
    ): UserResponseDto<UserDto>

    companion object{
        private const val QUERY_PHONE_NUMBER = "phone_number"
    }
}