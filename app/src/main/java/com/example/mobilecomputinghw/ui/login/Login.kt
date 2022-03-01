package com.example.mobilecomputinghw.ui.login

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobilecomputinghw.R
import com.example.mobilecomputinghw.data.entity.SPreference
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Login(
    navController: NavController,
    context: Context
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val username = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .heightIn(max = 24.dp)
                    )
                },
                backgroundColor = MaterialTheme.colors.secondary,
                actions = {
                    Button(
                        onClick = { navController.navigate(route = "register") },
                        modifier = Modifier.size(120.dp)
                    ) {
                        Text("Sign Up")
                    }
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(60.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
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
                    LoginCheck(
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
                Text(text = "Login")
            }
        }
    }
}


fun LoginCheck(
    try_username: String,
    try_password: String,
    navController: NavController,
    context: Context
) {
    val sharedPreference = SPreference(context)
    val comparison = sharedPreference.sharedPreference.contains(try_username + try_password)
    if (comparison == true) {
        sharedPreference.editor.putString("currentAccount", try_username)
        sharedPreference.editor.commit()
        return navController.navigate("home")
    } else
        return
}
