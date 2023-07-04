package com.agathakazak.chatme.domain.repository

import com.agathakazak.chatme.domain.entity.Chat
import com.agathakazak.chatme.domain.entity.Message
import com.agathakazak.chatme.domain.entity.MessageRequest
import com.agathakazak.chatme.domain.entity.Response
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.entity.UserLogin
import com.agathakazak.chatme.domain.entity.UserRegister

interface UserRepository {
    suspend fun registerUser(user: UserRegister)
    suspend fun loginUser(userLogin: UserLogin): Response<String>
    suspend fun getUserByToken(token: String): User
    suspend fun getUserByPhoneNumber(phoneNumber: String): User
    suspend fun getUserById(userId: Int): User
    suspend fun getChatsForUser(userId: Int): List<Chat>
    suspend fun getChat(senderId: Int, recipientId: Int): List<Message>
    suspend fun sendMessage(messageRequest: MessageRequest)
    suspend fun deleteMessages(ids: List<Int>)
    suspend fun readMessages(id: Int)
    suspend fun getUnreadedMessages(senderId: Int, recipientId: Int): List<Message>
}