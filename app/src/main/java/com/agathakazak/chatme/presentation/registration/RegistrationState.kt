package com.agathakazak.chatme.presentation.registration

sealed class RegistrationState(val response: String? = null) {
    object Initial : RegistrationState()
    object IsRegistered : RegistrationState()
    object NotRegistered : RegistrationState()
    class IsRegistrationError(response: String) : RegistrationState(response)
    object Loading : RegistrationState()
}
