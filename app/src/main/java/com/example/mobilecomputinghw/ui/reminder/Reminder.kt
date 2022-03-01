package com.example.mobilecomputinghw.ui.reminder


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.launch


@Composable
fun Reminder(
    onBackPress: () -> Unit,
    viewModel: ReminderViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val message = rememberSaveable { mutableStateOf(value = "") }
    val iconss = rememberSaveable { mutableStateOf(value = "notifications") }
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(onClick = onBackPress) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(60.dp)
                    .systemBarsPadding()
            ) {
                OutlinedTextField(
                    value = message.value,
                    onValueChange = { data -> message.value = data },
                    label = { Text(text = "Message")},
                )
                Spacer(modifier = Modifier.height(20.dp))
                IconDropdown(iconString = iconss)
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveReminder(
                                com.example.mobilecomputinghw.data.entity.Reminder(
                                    message = message.value,
                                    locationX = 2.0,
                                    locationY = 3.0,
                                    reminderTime = 15,
                                    creationTime = 13,
                                    creatorId = 2,
                                    reminderSeen = 14,
                                    reminderIcon = iconss.value
                                )

                            )
                        }
                        onBackPress()
                    },
                    enabled = true,
                    modifier = Modifier
                        .height(55.dp)
                        .width(150.dp),
                    shape = MaterialTheme.shapes.large,
                ) {
                    Text(text = "Add reminder")
                }
            }
        }

    }

}

@Composable
private fun IconDropdown(
    iconString: MutableState<String>
) {
    var expanded by remember { mutableStateOf(false)}

    var icons = if (iconString.value == "notifications") { Icons.Default.Notifications
    } else if (iconString.value == "call") { Icons.Default.Call }
    else if (iconString.value == "fastfood"){ Icons.Default.Fastfood }
    else { Icons.Default.Notifications }

    val icon = if (expanded) {
        Icons.Filled.ArrowDropUp
    } else {
        Icons.Filled.ArrowDropDown
    }

    Column {
        Row{
        Icon(
            imageVector = icons,
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .clickable { expanded = !expanded }
        )
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .clickable { expanded = !expanded }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                iconString.value = "notifications"
            }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    modifier = Modifier.size(45.dp)
                )
            }
            DropdownMenuItem(onClick = {
                expanded = false
                iconString.value = "call"
            }) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    modifier = Modifier.size(45.dp)
                )
            }
            DropdownMenuItem(onClick = {
                expanded = false
                iconString.value = "fastfood"
            }) {
                Icon(
                    imageVector = Icons.Default.Fastfood,
                    contentDescription = null,
                    modifier = Modifier.size(45.dp)
                )
            }

        }
        }
    }
}