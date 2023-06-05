package com.agathakazak.chatme.data.mapper

import com.agathakazak.chatme.data.model.ChatDto
import com.agathakazak.chatme.data.model.IdsListDto
import com.agathakazak.chatme.data.model.MessageDto
import com.agathakazak.chatme.data.model.MessageRequestDto
import com.agathakazak.chatme.data.model.ResponseDto
import com.agathakazak.chatme.data.model.UserDto
import com.agathakazak.chatme.data.model.UserLoginDto
import com.agathakazak.chatme.data.model.UserRegisterDto
import com.agathakazak.chatme.domain.entity.Chat
import com.agathakazak.chatme.domain.entity.Message
import com.agathakazak.chatme.domain.entity.MessageRequest
import com.agathakazak.chatme.domain.entity.Response
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.entity.UserLogin
import com.agathakazak.chatme.domain.entity.UserRegister
import javax.inject.Inject

class UserMapper @Inject constructor() {
    fun mapUserModelToDto(user: User) = UserDto(
        id = user.id,
        firstName = user.firstName,
        lastName = user.lastName,
        phoneNumber = user.phoneNumber,
        email = user.email,
        imageUrl = user.imageUrl,
        stubImageColor = user.stubImageColor
    )

    fun <T> mapResponseDtoToModel(responseDto: ResponseDto<T>) = Response<T>(
        data = responseDto.data
    )

    fun mapUserDtoToModel(userDto: UserDto) = User(
        id = userDto.id,
        firstName = userDto.firstName,
        lastName = userDto.lastName,
        phoneNumber = userDto.phoneNumber,
        email = userDto.email,
        imageUrl = userDto.imageUrl,
        stubImageColor = userDto.stubImageColor
    )

    fun mapUserLoginModelToDto(userLogin: UserLogin) = UserLoginDto(
        numberOrEmail = userLogin.numberOrEmail,
        password = userLogin.password
    )

    private fun mapMessageDtoToModel(messageDto: MessageDto) = Message(
        id = messageDto.id,
        senderId = messageDto.senderId,
        recipientId = messageDto.recipientId,
        messageText = messageDto.messageText,
        date = messageDto.date,
        attachmentId = messageDto.attachmentId,
        isUnread = messageDto.isUnread
    )

    fun mapMessageDtoListToModelList(messageDtoList: List<MessageDto>): List<Message> {
        return messageDtoList.map { mapMessageDtoToModel(it) }
    }

    fun mapMessageModelToDto(message: Message) = MessageDto(
        id = message.id,
        senderId = message.senderId,
        recipientId = message.recipientId,
        messageText = message.messageText,
        date = message.date,
        attachmentId = message.attachmentId,
        isUnread = message.isUnread
    )

    fun mapMessageRequestModelToDto(messageRequest: MessageRequest) = MessageRequestDto(
        senderId = messageRequest.senderId,
        recipientId = messageRequest.recipientId,
        messageText = messageRequest.messageText,
        attachmentId = messageRequest.attachmentId
    )

    fun mapIdsListToDto(ids: List<Int>) = IdsListDto(ids)

    fun mapUserRegisterModelToDto(userRegister: UserRegister) = UserRegisterDto(
        firstName = userRegister.firstName,
        lastName = userRegister.lastName,
        phoneNumber = userRegister.phoneNumber,
        email = userRegister.email,
        password = userRegister.password,
        imageUrl = userRegister.imageUrl,
        stubImageColor = userRegister.stubImageColor
    )

    fun mapChatDtoToModel(chatsDto: ChatDto, companion: UserDto): Chat {
        return Chat(
            companion = mapUserDtoToModel(companion),
            lastMessage = chatsDto.lastMessage,
            isUnread = chatsDto.isUnread
        )
    }
}