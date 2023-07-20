package com.agathakazak.chatme.data.mapper

import com.agathakazak.chatme.data.model.ChatDetailDto
import com.agathakazak.chatme.data.model.ChatDto
import com.agathakazak.chatme.data.model.IdsListDto
import com.agathakazak.chatme.data.model.MessageDto
import com.agathakazak.chatme.data.model.MessageRequestDto
import com.agathakazak.chatme.data.model.ResponseDto
import com.agathakazak.chatme.data.model.UserDto
import com.agathakazak.chatme.data.model.UserLoginDto
import com.agathakazak.chatme.data.model.UserRegisterDto
import com.agathakazak.chatme.domain.entity.Chat
import com.agathakazak.chatme.domain.entity.ChatDetail
import com.agathakazak.chatme.domain.entity.Message
import com.agathakazak.chatme.domain.entity.MessageRequest
import com.agathakazak.chatme.domain.entity.Response
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.entity.UserLogin
import com.agathakazak.chatme.domain.entity.UserRegister
import java.time.Instant
import java.util.Date
import javax.inject.Inject

class ChatMeMapper @Inject constructor() {
    fun <T> mapResponseDtoToModel(responseDto: ResponseDto<T>) = Response(
        data = responseDto.data
    )

    fun mapUserDtoToModel(userDto: UserDto) = User(
        id = userDto.id,
        firstName = userDto.firstName,
        lastName = userDto.lastName,
        phoneNumber = userDto.phoneNumber,
        email = userDto.email,
        imageUrl = userDto.imageUrl
    )

    fun mapUserLoginModelToDto(userLogin: UserLogin) = UserLoginDto(
        numberOrEmail = userLogin.numberOrEmail,
        password = userLogin.password
    )

    private fun mapMessageDtoToModel(messageDto: MessageDto) = Message(
        id = messageDto.id,
        senderId = messageDto.senderId,
        chatId = messageDto.chatId,
        messageText = messageDto.messageText,
        date = Date(messageDto.date),
        attachmentId = messageDto.attachmentId,
        isUnread = messageDto.isUnread
    )

    fun mapMessageDtoListToModelList(messageDtoList: List<MessageDto>): List<Message> {
        return messageDtoList.map { mapMessageDtoToModel(it) }
    }

    fun mapIdsListToDto(ids: List<Int>) = IdsListDto(ids)

    fun mapUserRegisterModelToDto(userRegister: UserRegister) = UserRegisterDto(
        firstName = userRegister.firstName,
        lastName = userRegister.lastName,
        phoneNumber = userRegister.phoneNumber,
        email = userRegister.email,
        password = userRegister.password,
        imageUrl = userRegister.imageUrl
    )

    fun mapChatDtoToModel(chatsDto: ChatDto): Chat {
        return Chat(
            id = chatsDto.id,
            chatName = chatsDto.chatName,
            chatImageUrl = chatsDto.chatImageUrl,
            stubImageColor = chatsDto.stubImageColor,
            lastMessage = mapMessageDtoToModel(chatsDto.lastMessage)
        )
    }

    fun mapChatDetailDtoToModel(chatDetailDto: ChatDetailDto): ChatDetail {
        return ChatDetail(
            chatName = chatDetailDto.chatName,
            chatImageUrl = chatDetailDto.chatImageUrl,
            stubImageColor = chatDetailDto.stubImageColor,
            members = chatDetailDto.members.map {
                mapUserDtoToModel(it)
            },
            messages = chatDetailDto.messages.map {
                mapMessageDtoToModel(it)
            },
        )
    }
}