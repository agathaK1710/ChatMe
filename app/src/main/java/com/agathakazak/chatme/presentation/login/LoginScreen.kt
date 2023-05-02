package com.agathakazak.chatme.presentation.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
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
import com.agathakazak.chatme.domain.entity.UserLogin
import com.agathakazak.chatme.presentation.ViewModelFactory
import com.agathakazak.chatme.presentation.isValidEmail
import com.agathakazak.chatme.presentation.isValidPassword
import com.agathakazak.chatme.presentation.isValidPhoneNumber

@Composable
fun LoginScreen(
    onClickSignUp: () -> Unit,
    navigateToMainScreen: () -> Unit,
    viewModelFactory: ViewModelFactory
) {
    val viewModel: LoginViewModel = viewModel(factory = viewModelFactory)
    val loginState = viewModel.loginState.observeAsState(LoginState.Initial)
    val context = LocalContext.current
    var numberOrEmail by rememberSaveable { mutableStateOf("") }
    var numberOrEmailError by rememberSaveable { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState(), reverseScrolling = true)
                .background(MaterialTheme.colors.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = stringResource(R.string.login_icon),
                    modifier = Modifier
                        .width(500.dp)
                        .padding(60.dp)
                        .align(Alignment.CenterHorizontally),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primaryVariant),
                )

                TextField(
                    value = numberOrEmail,
                    onValueChange = {
                        numberOrEmail = it
                        numberOrEmailError =
                            !isValidEmail(numberOrEmail) && !isValidPhoneNumber(numberOrEmail)
                    },
                    label = {
                        Text(
                            if (numberOrEmailError) {
                                stringResource(R.string.number_email_error)
                            } else {
                                stringResource(R.string.number_email)
                            }
                        )
                    },
                    singleLine = true,
                    isError = numberOrEmailError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.background,
                        textColor = MaterialTheme.colors.onPrimary
                    )
                )
                SetErrors(numberOrEmail, numberOrEmailError)

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

                TextButton(
                    onClick = {
                        if (numberOrEmail.isBlank()) numberOrEmailError = true
                        if (password.isBlank()) passwordError = true
                        if (!numberOrEmailError && !passwordError) {
                            viewModel.loginUser(UserLogin(numberOrEmail, password))
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.primaryVariant
                    ),
                    modifier = Modifier
                        .padding(top = 110.dp, start = 20.dp, end = 20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.sign_in),
                        fontSize = 20.sp,
                        fontFamily = FontFamily(listOf(Font(R.font.azoft_sans)))
                    )
                }
            }
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp)
            ) {
                Text(
                    stringResource(R.string.no_account_question),
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(top = 12.dp),
                    textAlign = TextAlign.Center
                )
                TextButton(
                    onClick = {
                        viewModel.changeLoginState(LoginState.NotLogged)
                        onClickSignUp()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.primaryVariant
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.sign_up),
                        fontFamily = FontFamily(listOf(Font(R.font.azoft_sans)))
                    )
                }
            }
        }
        CheckLoginState(loginState, context, navigateToMainScreen)
    }
}

@Composable
private fun SetErrors(numberOrEmail: String, numberOrEmailError: Boolean) {
    if (numberOrEmail.matches(Regex("\\+[1-9]*"))) {
        if (!isValidPhoneNumber(numberOrEmail)) {
            ShowError(numberOrEmailError, stringResource(R.string.phone_error_message))
        }
    } else if (numberOrEmail.isNotBlank()) {
        if (!isValidEmail(numberOrEmail)) {
            ShowError(numberOrEmailError, stringResource(R.string.email_error_message))
        }
    } else if (!isValidPhoneNumber(numberOrEmail) || !isValidPhoneNumber(numberOrEmail)) {
        ShowError(
            numberOrEmailError,
            stringResource(R.string.number_email_error_message)
        )
    }
}

@Composable
private fun CheckLoginState(
    loginState: State<LoginState>,
    context: Context,
    navigateToMainScreen: () -> Unit
) {
    when (loginState.value) {
        is LoginState.IsLogged -> {
            navigateToMainScreen()
        }
        is LoginState.IsLoggingError -> {
            LaunchedEffect(key1 = loginState) {
                Toast.makeText(context, loginState.value.response?.data, Toast.LENGTH_SHORT).show()
            }
        }
        is LoginState.Loading -> {
            CircularProgressIndicator(color = MaterialTheme.colors.secondary)
        }
        else -> {

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