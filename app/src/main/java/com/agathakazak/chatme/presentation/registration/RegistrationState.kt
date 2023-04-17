package com.agathakazak.chatme.presentation.registration

import com.agathakazak.chatme.domain.UserResponse

sealed class RegistrationState(val response: UserResponse<String>? = null) {
    object Initial : RegistrationState()
    object IsRegistered : RegistrationState()
    object NotRegistered : RegistrationState()
    class IsRegistrationError(response: UserResponse<String>) : RegistrationState(response)
    object Loading : RegistrationState()
}
