package com.agathakazak.chatme.presentation.registration

import com.agathakazak.chatme.domain.entity.SimpleResponse

sealed class RegistrationState(val response: SimpleResponse<String>? = null) {
    object Initial : RegistrationState()
    object IsRegistered : RegistrationState()
    object NotRegistered : RegistrationState()
    class IsRegistrationError(response: SimpleResponse<String>) : RegistrationState(response)
    object Loading : RegistrationState()
}
