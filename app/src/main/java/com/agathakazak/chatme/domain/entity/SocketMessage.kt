package com.agathakazak.chatme.domain.entity

data class SocketMessage<T>(
    val data: T,
    val type: MessageType
)
