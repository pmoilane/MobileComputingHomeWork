package com.example.mobilecomputinghw.ui.reminder


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.work.*
import android.content.Context
import android.location.Location
import android.os.Build
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mobilecomputinghw.Graph
import com.example.mobilecomputinghw.R
import com.example.mobilecomputinghw.util.NotificationWorker
import com.google.accompanist.insets.systemBarsPadding
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants
import java.lang.System.currentTimeMillis
import java.util.*
import java.util.concurrent.TimeUnit


@Composable
fun Reminder(
    onBackPress: () -> Unit,
    viewModel: ReminderViewModel = viewModel(),
    context: Context,
    navController: NavController,
    currentLocation: LatLng
) {
    val coroutineScope = rememberCoroutineScope()
    val message = rememberSaveable { mutableStateOf(value = "") }
    val iconString = rememberSaveable { mutableStateOf(value = "notifications") }
    val reminderTime = rememberSaveable { mutableStateOf(value = "no time")}
    val reminderTimeLong = rememberSaveable { mutableStateOf(0L)}
    val notificationSwitch = remember { mutableStateOf(true)}
    val notificationLocationBoolean = remember { mutableStateOf(true)}

    val latlng = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data")
        ?.value

    val latlngReminder = if (latlng == null) {
        LatLng(65.059, 25.466)
    } else  {
        latlng
    }

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
                    .systemBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = message.value,
                    onValueChange = { data -> message.value = data },
                    label = { Text(text = "Message") },
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row() {
                    pickTime(
                        context = context,
                        time = reminderTime,
                        timeLong = reminderTimeLong
                    )
                    Spacer(modifier = Modifier.width(47.dp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {

                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = String.format(
                            "Lat: %1$.2f,\n Lng: %2$.2f",
                            latlngReminder.latitude,
                            latlngReminder.longitude)
                    )
                    Spacer(modifier = Modifier.width(25.dp))
                    Button(
                        onClick = { navController.navigate("map") },
                        modifier = Modifier
                            .height(55.dp)
                            .width(105.dp),
                        shape = MaterialTheme.shapes.large,
                    ) {
                        Text(text = "Reminder location")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Switch(checked = notificationLocationBoolean.value,
                        onCheckedChange = { notificationLocationBoolean.value = it }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row() {
                    Text(text = "Notifications:")
                    Spacer(modifier = Modifier.width(20.dp))
                    Switch(checked = notificationSwitch.value,
                    onCheckedChange = { notificationSwitch.value = it }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                IconDropdown(iconString = iconString)
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveReminder(
                                com.example.mobilecomputinghw.data.entity.Reminder(
                                    message = message.value,
                                    locationX = latlngReminder.latitude,
                                    locationY = latlngReminder.longitude,
                                    reminderTime = reminderTimeLong.value,
                                    creationTime = currentTimeMillis(),
                                    creatorId = 2,
                                    reminderSeen = 14,
                                    reminderIcon = iconString.value
                                )

                            )
                        }
                        onBackPress()
                        if (notificationSwitch.value) {
                            createNotificationChannel(context = Graph.appContext)
                            setOneTimeNotification(reminderTimeLong = reminderTimeLong.value,
                            notificationMessage = message.value,
                            notificationTime = reminderTime.value,
                            latlngReminder = latlngReminder,
                            notificationLocationBoolean = notificationLocationBoolean,
                            currentLocation = currentLocation)
                        }
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
fun pickTime(
    context: Context,
    time: MutableState<String>,
    timeLong: MutableState<Long>
) {
    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val timePicker = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            time.value = String.format("%02d:%02d", hour, minute)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            timeLong.value = calendar.timeInMillis
        }, hour, minute, true
    )

    Row(
    ) {
        Text(text = "Time: ${time.value}")
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = {
            timePicker.show()
        },
            shape = MaterialTheme.shapes.large,
        ) {
            Text(text = "Pick Time")
        }
    }

}


@Composable
private fun IconDropdown(
    iconString: MutableState<String>
) {
    var expanded by remember { mutableStateOf(false) }

    val icons = if (iconString.value == "notifications") {
        Icons.Default.Notifications
    } else if (iconString.value == "call") {
        Icons.Default.Call
    } else if (iconString.value == "fastfood") {
        Icons.Default.Fastfood
    } else {
        Icons.Default.Notifications
    }

    val icon = if (expanded) {
        Icons.Filled.ArrowDropUp
    } else {
        Icons.Filled.ArrowDropDown
    }

    Column {
        Row {
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

private fun setOneTimeNotification(
    reminderTimeLong: Long,
    notificationMessage: String,
    notificationTime: String,
    latlngReminder: LatLng,
    notificationLocationBoolean: MutableState<Boolean>,
    currentLocation: LatLng
) {
    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(reminderTimeLong - currentTimeMillis(), TimeUnit.MILLISECONDS)
        .setConstraints(constraints)
        .build()

    workManager.enqueue(notificationWorker)
    if (notificationLocationBoolean.value){
    val distance = FloatArray(1)
    //Monitoring for state of work
    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever { workInfo ->
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                Location.distanceBetween(currentLocation.latitude,currentLocation.longitude,
                    latlngReminder.latitude, latlngReminder.longitude, distance)
                if (distance[0] <= 50) {
                    createSuccessNotification(
                        notificationMessage = notificationMessage,
                        notificationTime = notificationTime
                    )
                }
            }
        }
    }else{
        workManager.getWorkInfoByIdLiveData(notificationWorker.id)
            .observeForever { workInfo ->
                if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                    createSuccessNotification(
                        notificationMessage = notificationMessage,
                        notificationTime = notificationTime
                    )
                }
            }
    }

}

private fun createNotificationChannel(context: Context) {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "NotificationChannelName"
        val descriptionText = "NotificationChannelDescriptionText"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        // register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

private fun createSuccessNotification(
    notificationMessage: String,
    notificationTime: String
) {
    val notificationId = 1
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("Reminder $notificationTime")
        .setContentText(notificationMessage)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(Graph.appContext)) {
        //notificationId is unique for each notification that you define
        notify(notificationId, builder.build())
    }
}
