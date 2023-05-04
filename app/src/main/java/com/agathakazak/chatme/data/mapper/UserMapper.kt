package com.agathakazak.chatme.data.mapper

import com.agathakazak.chatme.data.model.MessageDto
import com.agathakazak.chatme.data.model.UserDto
import com.agathakazak.chatme.data.model.UserLoginDto
import com.agathakazak.chatme.data.model.SimpleResponseDto
import com.agathakazak.chatme.domain.entity.Message
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.entity.UserLogin
import com.agathakazak.chatme.domain.entity.SimpleResponse
import javax.inject.Inject

class UserMapper @Inject constructor() {
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

    fun <T> mapSimpleResponseDtoToModel(simpleResponseDto: SimpleResponseDto<T>): SimpleResponse<T> {
        return SimpleResponse(
            success = simpleResponseDto.success,
            data = simpleResponseDto.data
        )
    }

    fun mapSimpleResponseDtoWithUserDtoToModel(userResponseDto: SimpleResponseDto<UserDto>): SimpleResponse<User> {
        return SimpleResponse(
            success = userResponseDto.success,
            data = mapUserDtoToModel(userResponseDto.data)
        )
    }

    fun mapMessageDtoToModel(messageDto: MessageDto) = Message(
        senderId = messageDto.senderId,
        recipientId = messageDto.recipientId,
        messageText = messageDto.messageText,
        date = messageDto.date,
        attachmentId = messageDto.attachmentId,
        isUnread = messageDto.isUnread
    )

    fun mapMessageModelToDto(message: Message) = MessageDto(
        senderId = message.senderId,
        recipientId = message.recipientId,
        messageText = message.messageText,
        date = message.date,
        attachmentId = message.attachmentId,
        isUnread = message.isUnread
    )

    fun mapMessageResponse(messageResponse: SimpleResponseDto<List<MessageDto>>):
            SimpleResponse<List<Message>> {
        return SimpleResponse(
            success = messageResponse.success,
            data = messageResponse.data.map {
                mapMessageDtoToModel(it)
            }
        )
    }
}