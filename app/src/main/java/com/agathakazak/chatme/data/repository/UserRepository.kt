package com.agathakazak.chatme.data.repository

import com.agathakazak.chatme.data.mapper.UserMapper
import com.agathakazak.chatme.data.network.ApiFactory
import com.agathakazak.chatme.domain.User
import com.agathakazak.chatme.domain.UserLogin
import com.agathakazak.chatme.domain.UserResponse

class UserRepository {
    private val mapper = UserMapper()
    private val apiService = ApiFactory.apiService
    suspend fun registerUser(user: User): UserResponse<String> {
        return mapper.mapUserResponseDtoToModel(
            apiService.registerUser(mapper.mapUserModelToDto(user))
        )
    }

    suspend fun loginUser(userLogin: UserLogin): UserResponse<String> {
        return mapper.mapUserResponseDtoToModel(
            apiService.loginUser(
                mapper.mapUserLoginModelToDto(
                    userLogin
                )
            )
        )
    }

    suspend fun getUserByPhoneNumber(phoneNumber: String): UserResponse<User>{
        return mapper.mapUserResponseDtoWithDataDtoToModel(
            apiService.getUserByPhone(phoneNumber)
        )
    }
}