package com.agathakazak.chatme.data.repository

import com.agathakazak.chatme.data.mapper.UserMapper
import com.agathakazak.chatme.data.network.ApiService
import com.agathakazak.chatme.domain.entity.Chat
import com.agathakazak.chatme.domain.entity.Message
import com.agathakazak.chatme.domain.entity.MessageRequest
import com.agathakazak.chatme.domain.entity.Response
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.entity.UserLogin
import com.agathakazak.chatme.domain.entity.UserRegister
import com.agathakazak.chatme.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val mapper: UserMapper,
    private val apiService: ApiService
) : UserRepository {
    override suspend fun registerUser(user: UserRegister) {
        return apiService.registerUser(mapper.mapUserRegisterModelToDto(user))
    }

    override suspend fun loginUser(userLogin: UserLogin): Response<String> {
        return mapper.mapResponseDtoToModel(
            apiService.loginUser(
                mapper.mapUserLoginModelToDto(
                    userLogin
                )
            )
        )
    }

    override suspend fun getUserByToken(token: String): User {
        return mapper.mapUserDtoToModel(apiService.getUserByToken(token))
    }

    override suspend fun getUserByPhoneNumber(phoneNumber: String): User {
        return mapper.mapUserDtoToModel(
            apiService.getUserByPhone(phoneNumber)
        )
    }

    override suspend fun getUserById(userId: Int): User {
        return mapper.mapUserDtoToModel(apiService.getUserById(userId))
    }

    override suspend fun getChatsForUser(userId: Int): List<Chat> {
        return apiService.getChatsForUser(userId).map {
            val companionUser = apiService.getUserById(it.companionId)
            mapper.mapChatDtoToModel(it, companionUser)
        }
    }

    override suspend fun getChat(senderId: Int, recipientId: Int): List<Message> {
        return mapper.mapMessageDtoListToModelList(apiService.getChat(senderId, recipientId))
    }

    override suspend fun sendMessage(
        messageRequest: MessageRequest
    ) {
        return apiService.sendMessage(
            mapper.mapMessageRequestModelToDto(messageRequest)
        )
    }

    override suspend fun deleteMessages(ids: List<Int>) {
        apiService.deleteMessages(mapper.mapIdsListToDto(ids))
    }
}