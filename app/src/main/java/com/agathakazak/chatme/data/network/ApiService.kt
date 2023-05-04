package com.agathakazak.chatme.data.network

import com.agathakazak.chatme.data.model.MessageDto
import com.agathakazak.chatme.data.model.UserDto
import com.agathakazak.chatme.data.model.UserLoginDto
import com.agathakazak.chatme.data.model.SimpleResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    suspend fun registerUser(
        @Body user: UserDto
    ): SimpleResponseDto<String>

    @POST("login")
    suspend fun loginUser(
        @Body userLogin: UserLoginDto
    ): SimpleResponseDto<String>

    @GET("user")
    suspend fun getUserByPhone(
        @Query(QUERY_PHONE_NUMBER, encoded = true) phoneNumber: String
    ): SimpleResponseDto<UserDto>

    @POST("message")
    suspend fun sendMessage(
        @Query(QUERY_RECIPIENT_ID) recipient_id: Int,
        @Body message: MessageDto
    ): SimpleResponseDto<String>

    @GET("chat")
    suspend fun getChat(
        @Query(QUERY_RECIPIENT_ID) recipient_id: Int
    ): SimpleResponseDto<List<MessageDto>>

    companion object{
        private const val QUERY_PHONE_NUMBER = "phone_number"
        private const val QUERY_RECIPIENT_ID = "recipient_id"
    }
}