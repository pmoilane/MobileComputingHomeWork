package com.example.mobilecomputinghw.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobilecomputinghw.ui.login.Login
import com.example.mobilecomputinghw.ui.home.Home
import com.example.mobilecomputinghw.ui.profile.Profile
import com.example.mobilecomputinghw.ui.register.Register
import com.example.mobilecomputinghw.ui.reminder.Reminder

@Composable
fun MobileComputingHWApp(
    appState: MobileComputingHWAppState = rememberMobileComputingHWAppState(),
    context: Context
) {
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
                navController = appState.navController
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
                context = context
            )
        }
    }
}