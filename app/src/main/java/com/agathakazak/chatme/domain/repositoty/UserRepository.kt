package com.agathakazak.chatme.domain.repositoty

import com.agathakazak.chatme.domain.entity.Message
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.entity.UserLogin
import com.agathakazak.chatme.domain.entity.SimpleResponse

interface UserRepository {
    suspend fun registerUser(user: User): SimpleResponse<String>
    suspend fun loginUser(userLogin: UserLogin): SimpleResponse<String>
    suspend fun getUserByPhoneNumber(phoneNumber: String): SimpleResponse<User>
    suspend fun getChat(recipientId: Int): SimpleResponse<List<Message>>
    suspend fun sendMessage(message: Message, recipientId: Int): SimpleResponse<String>
}