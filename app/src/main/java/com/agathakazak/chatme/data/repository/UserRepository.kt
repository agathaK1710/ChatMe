package com.agathakazak.chatme.data.repository

import com.agathakazak.chatme.data.mapper.UserMapper
import com.agathakazak.chatme.data.network.ApiFactory
import com.agathakazak.chatme.domain.User

class UserRepository {
    private val mapper = UserMapper()
    suspend fun registerUser(user: User): String {
        return ApiFactory.apiService.registerUser(mapper.mapUserModelToDto(user)).message
    }
}