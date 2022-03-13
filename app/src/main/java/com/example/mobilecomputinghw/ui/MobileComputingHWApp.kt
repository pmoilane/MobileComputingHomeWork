package com.example.mobilecomputinghw.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobilecomputinghw.ui.login.Login
import com.example.mobilecomputinghw.ui.home.Home
import com.example.mobilecomputinghw.ui.map.ReminderMap
import com.example.mobilecomputinghw.ui.profile.Profile
import com.example.mobilecomputinghw.ui.register.Register
import com.example.mobilecomputinghw.ui.reminder.Reminder
import com.google.android.gms.maps.model.LatLng

@Composable
fun MobileComputingHWApp(
    appState: MobileComputingHWAppState = rememberMobileComputingHWAppState(),
    context: Context
) {
    val latlng = remember { mutableStateOf(LatLng(65.059, 25.466)) }
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        composable(route = "login") {
            Login(
                navController = appState.navController
            )
        }
        composable(route = "home") {
            Home(
                navController = appState.navController
            )
        }
        composable(route = "profile") {
            Profile(
                navController = appState.navController,
                currentLocation = latlng
            )
        }
        composable(route = "register") {
            Register(
                navController = appState.navController,
                onBackPress = appState::navigateBack
            )
        }
        composable(route = "reminder") {
            Reminder(
                onBackPress = appState::navigateBack,
                context = context,
                navController = appState.navController,
                currentLocation = latlng.value
            )
        }
        composable(route = "map") {
            ReminderMap(navController = appState.navController,
            currentLocation = latlng.value)
        }
    }
}