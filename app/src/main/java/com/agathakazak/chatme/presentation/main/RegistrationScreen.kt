package com.agathakazak.chatme.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
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
import com.agathakazak.chatme.presentation.main.MainViewModel


@Composable
fun RegistrationScreen(context: Context) {
    val viewModel:MainViewModel = viewModel()
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var phoneError by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var repeatedPassword by rememberSaveable { mutableStateOf("") }
    var repeatedPasswordHidden by rememberSaveable { mutableStateOf(true) }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Registration",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp, top = 30.dp),
            fontSize = 50.sp,
            fontFamily = FontFamily(listOf(Font(R.font.cormorant_variable_font))),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text(text = "First name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(text = "Last name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                phoneError = false
            },
            label = {
                Text(if (phoneError) "Phone number*" else "Phone number")
            },
            singleLine = true,
            isError = phoneError,
            modifier = Modifier
                .semantics {
                    if (phoneError) error("Phone number format is invalid")
                }
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = email,
            onValueChange = {
                email = it
                emailError = false
            },
            label = {
                Text(if (emailError) "Email*" else "Email")
            },
            singleLine = true,
            isError = emailError,
            modifier = Modifier
                .semantics {
                    if (emailError) error("Email format is invalid")
                }
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Enter password")
            },
            singleLine = true,
            visualTransformation = if (passwordHidden) PasswordVisualTransformation()
            else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) painterResource(id = R.drawable.visibility)
                        else painterResource(id = R.drawable.visibility_off)
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(painter = visibilityIcon, contentDescription = description)
                }
            },
            modifier = Modifier
                .semantics {
                    if (emailError) error("Email format is invalid")
                }
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background)
        )

        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = repeatedPassword,
            onValueChange = {
                repeatedPassword = it
                passwordError = repeatedPassword != password
            },
            label = {
                Text(if (passwordError) "Repeat password*" else "Repeat password")
            },
            singleLine = true,
            isError = passwordError,
            visualTransformation = if (repeatedPasswordHidden) PasswordVisualTransformation()
            else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { repeatedPasswordHidden = !repeatedPasswordHidden }) {
                    val visibilityIcon =
                        if (repeatedPasswordHidden) painterResource(id = R.drawable.visibility)
                        else painterResource(id = R.drawable.visibility_off)
                    val description =
                        if (repeatedPasswordHidden) "Show password" else "Hide password"
                    Icon(painter = visibilityIcon, contentDescription = description)
                }
            },
            modifier = Modifier
                .semantics {
                    if (emailError) error("Email format is invalid")
                }
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background)
        )
        OutlinedButton(
            onClick = {
                registerUser(
                    viewModel,
                    User(firstName, lastName, phoneNumber, email, password)
                )
            },
            modifier = Modifier
                .padding(top = 80.dp)
                .fillMaxWidth(),
        ) {
            Text(text = "Sign Up")
        }
    }

}

private fun registerUser(viewModel: MainViewModel, user: User) {
    viewModel.registerUser(user)
}

