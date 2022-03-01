package com.example.mobilecomputinghw

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class MobileComputingHWAppState(
    val navController: NavHostController
) {
    fun navigateBack() {
        navController.popBackStack()
    }
}

@Composable
fun rememberMobileComputingHWAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    MobileComputingHWAppState(navController)
}