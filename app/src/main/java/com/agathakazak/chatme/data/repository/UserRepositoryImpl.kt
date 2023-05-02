package com.agathakazak.chatme.data.repository

import com.agathakazak.chatme.data.mapper.UserMapper
import com.agathakazak.chatme.data.network.ApiService
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.entity.UserLogin
import com.agathakazak.chatme.domain.entity.UserResponse
import com.agathakazak.chatme.domain.repositoty.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val mapper: UserMapper,
    private val apiService: ApiService
): UserRepository {
    override suspend fun registerUser(user: User): UserResponse<String> {
        return mapper.mapUserResponseDtoToModel(
            apiService.registerUser(mapper.mapUserModelToDto(user))
        )
    }

    override suspend fun loginUser(userLogin: UserLogin): UserResponse<String> {
        return mapper.mapUserResponseDtoToModel(
            apiService.loginUser(
                mapper.mapUserLoginModelToDto(
                    userLogin
                )
            )
        )
    }

    override suspend fun getUserByPhoneNumber(phoneNumber: String): UserResponse<User> {
        return mapper.mapUserResponseDtoWithDataDtoToModel(
            apiService.getUserByPhone(phoneNumber)
        )
    }
}