package com.agathakazak.chatme.presentation

fun isValidEmail(email: String): Boolean {
    val regex = Regex("[a-z0-9.]+@[a-z]+\\.[a-z]{2,3}")
    return email.matches(regex)
}

fun isValidPhoneNumber(phoneNumber: String): Boolean = phoneNumber.matches(Regex("\\+[1-9]{10,13}"))

fun isValidPassword(password: String): Boolean {
    return password.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$"))
}