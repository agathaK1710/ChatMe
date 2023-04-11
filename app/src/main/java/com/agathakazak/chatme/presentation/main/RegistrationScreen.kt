package com.agathakazak.chatme.presentation.main

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agathakazak.chatme.R
import com.agathakazak.chatme.domain.User
import com.agathakazak.chatme.presentation.isValidEmail
import com.agathakazak.chatme.presentation.isValidPassword
import com.agathakazak.chatme.presentation.isValidPhoneNumber


@Composable
fun RegistrationScreen(context: Context) {
    val viewModel: MainViewModel = viewModel()
    var firstName by rememberSaveable { mutableStateOf("") }
    var firstNameError by rememberSaveable { mutableStateOf(false) }
    var lastName by rememberSaveable { mutableStateOf("") }
    var lastNameError by rememberSaveable { mutableStateOf(false) }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var phoneError by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    var repeatedPassword by rememberSaveable { mutableStateOf("") }
    var repeatedPasswordHidden by rememberSaveable { mutableStateOf(true) }
    var repeatedPasswordError by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState(), reverseScrolling = true)
            .background(MaterialTheme.colors.background),
    ) {
        Image(
            painter = painterResource(id = R.drawable.registration),
            contentDescription = stringResource(R.string.reg_icon_content_description),
            modifier = Modifier
                .width(300.dp)
                .padding(40.dp)
                .align(Alignment.CenterHorizontally),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primaryVariant),
        )
        TextField(
            value = firstName,
            onValueChange = {
                firstName = it
                firstNameError = it.isBlank()
            },
            label = {
                Text(
                    text = if (!firstNameError) {
                        stringResource(R.string.first_name)
                    } else {
                        stringResource(R.string.first_name_error)
                    }
                )
            },
            singleLine = true,
            isError = firstNameError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                textColor = MaterialTheme.colors.onPrimary
            )
        )
        ShowError(firstNameError, stringResource(R.string.first_name_error_message))
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(text = if(!lastNameError) {
                stringResource(R.string.last_name)
            } else {
                stringResource(R.string.last_name_error)
            }) },
            singleLine = true,
            isError = lastNameError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                textColor = MaterialTheme.colors.onPrimary
            )
        )
        ShowError(lastNameError, stringResource(R.string.last_name_error_message))
        TextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                phoneError = !isValidPhoneNumber(phoneNumber)
            },
            label = {
                Text(
                    if (phoneError) {
                        stringResource(R.string.phone_number_error)
                    } else {
                        stringResource(R.string.phone_number)
                    }
                )
            },
            singleLine = true,
            isError = phoneError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                textColor = MaterialTheme.colors.onPrimary
            )
        )
        ShowError(
            phoneError,
            stringResource(R.string.phone_error_message)
        )
        TextField(
            value = email,
            onValueChange = {
                email = it
                emailError = !isValidEmail(email)
            },
            label = {
                Text(
                    if (emailError) {
                        stringResource(R.string.email_error)
                    } else {
                        stringResource(R.string.email)
                    }
                )
            },
            singleLine = true,
            isError = emailError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                textColor = MaterialTheme.colors.onPrimary
            )
        )
        ShowError(emailError, stringResource(R.string.email_error_message))
        TextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = !isValidPassword(password)
            },
            label = {
                Text(
                    if (passwordError) {
                        stringResource(R.string.password_error)
                    } else {
                        stringResource(R.string.password)
                    }
                )
            },
            singleLine = true,
            isError = passwordError,
            visualTransformation = if (passwordHidden) PasswordVisualTransformation()
            else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) painterResource(id = R.drawable.visibility)
                        else painterResource(id = R.drawable.visibility_off)
                    val description = if (passwordHidden) {
                        stringResource(R.string.show_password_content_description)
                    } else {
                        stringResource(R.string.hide_password_content_description)
                    }
                    Icon(painter = visibilityIcon, contentDescription = description)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                textColor = MaterialTheme.colors.onPrimary
            )
        )
        ShowError(
            passwordError,
            stringResource(R.string.password_error_message)
        )
        TextField(
            value = repeatedPassword,
            onValueChange = {
                repeatedPassword = it
                repeatedPasswordError = repeatedPassword != password
            },
            label = {
                Text(
                    if (repeatedPasswordError) {
                        stringResource(R.string.repeat_password_error)
                    } else {
                        stringResource(R.string.repeat_password)
                    }
                )
            },
            singleLine = true,
            isError = repeatedPasswordError,
            visualTransformation = if (repeatedPasswordHidden) PasswordVisualTransformation()
            else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { repeatedPasswordHidden = !repeatedPasswordHidden }) {
                    val visibilityIcon =
                        if (repeatedPasswordHidden) painterResource(id = R.drawable.visibility)
                        else painterResource(id = R.drawable.visibility_off)
                    val description =
                        if (repeatedPasswordHidden) {
                            stringResource(R.string.show_password_content_description)
                        } else {
                            stringResource(R.string.hide_password_content_description)
                        }
                    Icon(painter = visibilityIcon, contentDescription = description)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                textColor = MaterialTheme.colors.onPrimary
            )
        )
        ShowError(repeatedPasswordError, stringResource(R.string.repeated_password_error_message))
        TextButton(
            onClick = {
                if (firstName.isBlank()) firstNameError = true
                if (lastName.isBlank()) lastNameError = true
                if (phoneNumber.isBlank()) phoneError = true
                if (email.isBlank()) emailError = true
                if (password.isBlank()) passwordError = true
                if (repeatedPassword.isBlank()) repeatedPasswordError = true
                if (!firstNameError && !lastNameError && !phoneError
                    && !emailError && !passwordError && !repeatedPasswordError
                ) {
                    viewModel.registerUser(User(firstName, lastName, phoneNumber, email, password))
                }

            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primaryVariant
            ),
            modifier = Modifier
                .padding(top = 50.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                fontSize = 20.sp,
                fontFamily = FontFamily(listOf(Font(R.font.azoft_sans)))
            )
        }
    }

}

@Composable
private fun ShowError(isError: Boolean, errorText: String) {
    if (isError) {
        Text(
            text = errorText,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 8.dp),
            textAlign = TextAlign.Start
        )
    }
}
