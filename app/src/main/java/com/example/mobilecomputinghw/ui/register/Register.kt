package com.example.mobilecomputinghw.ui.register

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobilecomputinghw.data.entity.SPreference
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Register(
    navController: NavController,
    context: Context,
    onBackPress: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val username = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = onBackPress
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(text = "Register")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(60.dp)
                    .systemBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = username.value,
                    onValueChange = { data -> username.value = data },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { data -> password.value = data },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        SignUp(
                            try_username = username.value,
                            try_password = password.value,
                            navController = navController,
                            context = context
                        )
                    },
                    enabled = true,
                    modifier = Modifier
                        .height(55.dp)
                        .width(120.dp),
                    shape = MaterialTheme.shapes.large,
                ) {
                    Text(text = "Sign Up")
                }
            }
        }
    }
}

fun SignUp(
    try_username: String,
    try_password: String,
    navController: NavController,
    context: Context
) {
    val sharedPreference = SPreference(context)
    sharedPreference.editor.putString(try_username + try_password, try_username)
    sharedPreference.editor.commit()
    return navController.navigate("login")
}
