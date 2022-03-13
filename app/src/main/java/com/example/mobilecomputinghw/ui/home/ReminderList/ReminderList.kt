package com.example.mobilecomputinghw.ui.home.ReminderList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobilecomputinghw.data.entity.Reminder
import com.example.mobilecomputinghw.ui.reminder.ReminderViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@Composable
fun CategoryReminder(
    modifier: Modifier = Modifier,
    remindersSwitch: MutableState<Boolean>
) {
    val viewModel: CategoryReminderViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()
    val viewState2 by viewModel.state2.collectAsState()
    if (remindersSwitch.value){
    Column(modifier = modifier) {
        ReminderList(
            list = viewState.reminders
        )
    }
    } else{
            Column(modifier = modifier) {
                ReminderList(
                    list = viewState2.pastReminders
                )
        }
    }
}

@Composable
private fun ReminderList(
    list: List<Reminder>
) {
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(list) { item ->
            ReminderListItem(
                reminder = item,
                onClick = {},
                modifier = Modifier.fillParentMaxWidth(),
            )
        }
    }
}

@Composable
private fun ReminderListItem(
    reminder: Reminder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReminderViewModel = viewModel()
) {
    val dialogBoolean = remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, reminderTitle, reminderTime, iconEdit, iconRemove, icon) = createRefs()
        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        // title
        Text(
            text = reminder.message,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(reminderTitle) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )
        // reminderTime
        Text(
            text = longToTimeString(reminder.reminderTime),
            maxLines = 1,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(reminderTime) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin = 8.dp,
                    bias = 0f
                )
                top.linkTo(reminderTitle.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // icon
        IconButton(
            onClick = {
                dialogBoolean.value = true
            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(iconEdit) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(iconRemove.start)
                }
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
        }
        IconButton(
            onClick = {
                coroutineScope.launch {
                    viewModel.removeReminder(reminder)
                }
            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(iconRemove) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(icon.start)
                }
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = null
            )
        }
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = StringToImageVector(reminder.reminderIcon),
                contentDescription = null
            )
        }
        if (dialogBoolean.value) {
            val newMessage = remember {
                mutableStateOf(reminder.message)
            }
            AlertDialog(
                onDismissRequest = {
                    dialogBoolean.value = false
                },
                title = {
                    Text(text = "Edit reminder message")
                },
                text = {
                    Column() {
                        TextField(
                            value = newMessage.value,
                            onValueChange = { newMessage.value = it }
                        )
                    }
                },
                buttons = {
                    Row(
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                dialogBoolean.value = false
                                coroutineScope.launch {
                                    viewModel.editReminder(
                                        Reminder(
                                            reminderId = reminder.reminderId,
                                            message = newMessage.value,
                                            locationX = reminder.locationX,
                                            locationY = reminder.locationY,
                                            reminderTime = reminder.reminderTime,
                                            creationTime = reminder.creationTime,
                                            creatorId = reminder.creatorId,
                                            reminderSeen = reminder.reminderSeen,
                                            reminderIcon = reminder.reminderIcon
                                        )

                                    )
                                }
                            }
                        ) {
                            Text("Save changes")
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Button(
                            onClick = {
                                dialogBoolean.value = false
                            }
                        ) {
                            Text("Discard")
                        }

                    }
                }
            )
        }
    }
}

private fun StringToImageVector(IconString: String): ImageVector {
    if (IconString == "notifications") {
        return Icons.Default.Notifications
    }
    else if (IconString == "call") {
        return Icons.Default.Call
    }
    else if (IconString == "fastfood"){
        return Icons.Default.Fastfood
    }
    else {
        return Icons.Default.Notifications
    }
}

private fun longToTimeString(milliseconds: Long): String {
    if (milliseconds == 0L){
        return "no time set"
    }
    else {
        val milliseconds2 = milliseconds + 7200000L //adds 2 hours to have correct timezone
        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(milliseconds2) -
                    TimeUnit.MILLISECONDS.toDays(milliseconds2) * 24,
            TimeUnit.MILLISECONDS.toMinutes(milliseconds2)
                    - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds2))
        )
    }
}
