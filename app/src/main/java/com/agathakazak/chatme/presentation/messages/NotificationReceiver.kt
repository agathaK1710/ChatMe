package com.agathakazak.chatme.presentation.messages

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import com.agathakazak.chatme.R
import com.agathakazak.chatme.presentation.ChatMeApplication
import com.agathakazak.chatme.presentation.messages.MessagesViewModel.Companion.KEY_TEXT_REPLY
import com.agathakazak.chatme.presentation.messages.MessagesViewModel.Companion.MESSAGE
import com.agathakazak.chatme.presentation.messages.MessagesViewModel.Companion.channelId
import com.agathakazak.chatme.presentation.messages.MessagesViewModel.Companion.notificationId
import javax.inject.Inject


class NotificationReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notificationManager: NotificationManager
    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onReceive(context: Context?, intent: Intent?) {
        (context?.applicationContext as ChatMeApplication).component.inject(this)
        val inputText = intent?.let { getMessageText(it) }
        if (inputText != null) {
            sharedPreferences.edit().putString(MESSAGE, inputText.trim().toString()).apply()
            val person = Person.Builder().setName(context.getString(R.string.me)).build()
            val message = NotificationCompat.MessagingStyle.Message(
                inputText,
                System.currentTimeMillis(),
                person
            )
            val notificationStyle = NotificationCompat.MessagingStyle(person).addMessage(message)
            notificationManager.notify(
                notificationId,
                notificationBuilder
                    .setChannelId(channelId)
                    .setStyle(notificationStyle)
                    .build()
            )
        }
    }

    private fun getMessageText(intent: Intent): CharSequence? {
        return RemoteInput.getResultsFromIntent(intent)?.getCharSequence(KEY_TEXT_REPLY)
    }

}