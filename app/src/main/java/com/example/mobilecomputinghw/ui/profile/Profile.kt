package com.example.mobilecomputinghw.ui.profile

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobilecomputinghw.data.entity.SPreference
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Profile(
    navController: NavController,
    context: Context
) {
    val sharedPreference = SPreference(context)
    val currentAccount = sharedPreference.sharedPreference.getString("currentAccount", "error")
    val currentUsernameString = currentAccount.toString()
    Surface {
        val newUsername = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = { navController.navigate("home") }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(text = "Profile")
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(60.dp)
            ) {
                Text(
                    text = "Current username: $currentUsernameString",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(alignment = Alignment.Start)
                )
                OutlinedTextField(
                    value = newUsername.value,
                    onValueChange = { data -> newUsername.value = data },
                    label = { Text(text = "New username") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { data -> password.value = data },
                    label = { Text(text = "Password") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        changeUsername(
                            newUsername = newUsername.value,
                            password = password.value,
                            navController = navController,
                            context = context
                        )
                    },
                    enabled = true,
                    modifier = Modifier
                        .height(55.dp)
                        .width(160.dp),
                    shape = MaterialTheme.shapes.large,
                ) {
                    Text("Confirm changes")
                }

            }
        }
    }
}

fun changeUsername(
    newUsername: String,
    password: String,
    navController: NavController,
    context: Context
) {
    val sharedPreference = SPreference(context)
    val currentAccount = sharedPreference.sharedPreference.getString("currentAccount", "error")
    val comparison = sharedPreference.sharedPreference.contains(currentAccount + password)
    if (comparison == true) {
        sharedPreference.editor.remove(currentAccount + password)
        sharedPreference.editor.putString(newUsername + password, newUsername)
        sharedPreference.editor.putString("currentAccount", newUsername)
        sharedPreference.editor.commit()
        return navController.navigate("profile")
    } else
        return
}