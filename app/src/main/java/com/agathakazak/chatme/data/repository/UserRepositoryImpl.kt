package com.agathakazak.chatme.data.repository

import com.agathakazak.chatme.data.mapper.UserMapper
import com.agathakazak.chatme.data.network.ApiService
import com.agathakazak.chatme.domain.entity.Message
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.entity.UserLogin
import com.agathakazak.chatme.domain.entity.SimpleResponse
import com.agathakazak.chatme.domain.repositoty.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val mapper: UserMapper,
    private val apiService: ApiService
) : UserRepository {
    override suspend fun registerUser(user: User): SimpleResponse<String> {
        return mapper.mapSimpleResponseDtoToModel(
            apiService.registerUser(mapper.mapUserModelToDto(user))
        )
    }

    override suspend fun loginUser(userLogin: UserLogin): SimpleResponse<String> {
        return mapper.mapSimpleResponseDtoToModel(
            apiService.loginUser(
                mapper.mapUserLoginModelToDto(
                    userLogin
                )
            )
        )
    }

    override suspend fun getUserByPhoneNumber(phoneNumber: String): SimpleResponse<User> {
        return mapper.mapSimpleResponseDtoWithUserDtoToModel(
            apiService.getUserByPhone(phoneNumber)
        )
    }

    override suspend fun getChat(recipientId: Int): SimpleResponse<List<Message>> {
        return mapper.mapMessageResponse(apiService.getChat(recipientId))
    }

    override suspend fun sendMessage(
        message: Message,
        recipientId: Int
    ): SimpleResponse<String> {
        return mapper.mapSimpleResponseDtoToModel(apiService.sendMessage(
            recipientId,
            mapper.mapMessageModelToDto(message)
        ))
    }
}