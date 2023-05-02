package com.agathakazak.chatme.domain.repositoty

import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.entity.UserLogin
import com.agathakazak.chatme.domain.entity.UserResponse

interface UserRepository {
    suspend fun registerUser(user: User): UserResponse<String>
    suspend fun loginUser(userLogin: UserLogin): UserResponse<String>
    suspend fun getUserByPhoneNumber(phoneNumber: String): UserResponse<User>
}