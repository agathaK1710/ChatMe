package com.agathakazak.chatme.data.network

import com.agathakazak.chatme.data.model.ChatDto
import com.agathakazak.chatme.data.model.MessageDto
import com.agathakazak.chatme.data.model.MessageRequestDto
import com.agathakazak.chatme.data.model.ResponseDto
import com.agathakazak.chatme.data.model.UserDto
import com.agathakazak.chatme.data.model.UserLoginDto
import com.agathakazak.chatme.data.model.UserRegisterDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    suspend fun registerUser(
        @Body user: UserRegisterDto
    )

    @POST("login")
    suspend fun loginUser(
        @Body userLogin: UserLoginDto
    ): ResponseDto<String>

    @GET("me")
    suspend fun getUserByToken(
        @Header(HEADER_AUTHORIZATION) header: String
    ): UserDto

    @GET("user")
    suspend fun getUserByPhone(
        @Query(QUERY_PHONE_NUMBER, encoded = true) phoneNumber: String
    ): UserDto

    @GET("user/{id}")
    suspend fun getUserById(
        @Path(PATH_ID) uerId: Int
    ): UserDto

    @POST("message")
    suspend fun sendMessage(
        @Body message: MessageRequestDto
    )

    @GET("chats")
    suspend fun getChatsForUser(
        @Query(QUERY_SENDER_ID) senderId: Int
    ): List<ChatDto>

    @GET("chat")
    suspend fun getChat(
        @Query(QUERY_SENDER_ID) senderId: Int,
        @Query(QUERY_RECIPIENT_ID) recipientId: Int
    ): List<MessageDto>

    companion object {
        private const val QUERY_PHONE_NUMBER = "phoneNumber"
        private const val QUERY_SENDER_ID = "senderId"
        private const val QUERY_RECIPIENT_ID = "recipientId"
        private const val PATH_ID = "id"
        private const val HEADER_AUTHORIZATION = "Authorization"
    }
}