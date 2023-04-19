package com.agathakazak.chatme.data.mapper

import com.agathakazak.chatme.data.model.UserDto
import com.agathakazak.chatme.data.model.UserLoginDto
import com.agathakazak.chatme.data.model.UserResponseDto
import com.agathakazak.chatme.domain.User
import com.agathakazak.chatme.domain.UserLogin
import com.agathakazak.chatme.domain.UserResponse

class UserMapper {
    fun mapUserModelToDto(user: User) = UserDto(
        firstName = user.firstName,
        lastName = user.lastName,
        phoneNumber = user.phoneNumber,
        email = user.email,
        password = user.password,
        imageUrl = user.imageUrl
    )

    private fun mapUserDtoToModel(userDto: UserDto) = User(
        firstName = userDto.firstName,
        lastName = userDto.lastName,
        phoneNumber = userDto.phoneNumber,
        email = userDto.email,
        password = userDto.password,
        imageUrl = userDto.imageUrl
    )

    fun mapUserLoginModelToDto(userLogin: UserLogin) = UserLoginDto(
        numberOrEmail = userLogin.numberOrEmail,
        password = userLogin.password
    )

    fun <T> mapUserResponseDtoToModel(userResponseDto: UserResponseDto<T>): UserResponse<T> {
        return UserResponse(
            success = userResponseDto.success,
            data = userResponseDto.data
        )
    }

    fun mapUserResponseDtoWithDataDtoToModel(userResponseDto: UserResponseDto<UserDto>): UserResponse<User> {
        return UserResponse(
            success = userResponseDto.success,
            data = mapUserDtoToModel(userResponseDto.data)
        )
    }
}