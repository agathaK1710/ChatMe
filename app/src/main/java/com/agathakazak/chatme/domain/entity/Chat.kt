package com.agathakazak.chatme.domain.entity

data class Chat(
    val companion: User,
    val lastMessage: String,
    val isUnread: Boolean
)