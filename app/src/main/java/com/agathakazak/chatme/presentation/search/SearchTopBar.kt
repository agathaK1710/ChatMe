package com.agathakazak.chatme.presentation.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.agathakazak.chatme.R

@Composable
fun SearchTopBar(
    searchPressed: Boolean,
    searchText: String,
    focusRequester: FocusRequester,
    backClick: () -> Unit,
    menuClick: () -> Unit,
    searchTextChange: (String) -> Unit,
    searchClick: (Boolean) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White
            )
        },
        navigationIcon = {
            if (searchPressed) {
                IconButton(onClick = {
                    backClick()
                }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .size(25.dp),
                        tint = Color.White
                    )
                }
            } else {
                IconButton(onClick = {
                    menuClick()
                }) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .size(30.dp),
                        tint = Color.White
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        actions = {
            if(searchPressed){
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = searchText,
                    onValueChange = {
                        searchTextChange(it)
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        textColor = Color.White,
                        cursorColor = Color.White
                    ),
                    placeholder = {
                        Text(text = "Search contact", color = Color.White)
                    },
                    maxLines = 1,
                    singleLine = true,
                    trailingIcon = {
                        if (searchText.isNotBlank()) {
                            IconButton(onClick = {
                                searchTextChange("")
                            }) {
                                Icon(
                                    Icons.Filled.Close,
                                    null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                )
                LaunchedEffect(searchPressed) {
                    if (searchPressed) focusRequester.requestFocus()
                }
            }
            IconButton(onClick = {
                searchClick(true)
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = Color.White
                )
            }
        }
    )
}