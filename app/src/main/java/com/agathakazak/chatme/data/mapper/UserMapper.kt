package com.agathakazak.chatme.data.mapper

import com.agathakazak.chatme.data.model.UserDto
import com.agathakazak.chatme.domain.User

class UserMapper {
    fun mapUserModelToDto(user: User) = UserDto(
        firstName = user.firstName,
        lastName = user.lastName,
        phoneNumber = user.phoneNumber,
        email = user.email,
        password = user.password
    )
}