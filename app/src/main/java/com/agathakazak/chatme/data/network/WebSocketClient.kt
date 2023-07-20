package com.agathakazak.chatme.data.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.logging.HttpLoggingInterceptor

object WebSocketClient {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    fun createWebSocket(chatId: Int, userId: Int, socketListener: WebSocketListener): WebSocket{
        return okHttpClient.newWebSocket(
            Request.Builder().url("ws://10.0.2.2:8080/chat/${chatId}/${userId}").build(),
            socketListener
        )
    }
}