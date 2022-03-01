package com.example.mobilecomputinghw.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobilecomputinghw.R
import com.example.mobilecomputinghw.ui.home.categoryReminder.CategoryReminder
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Home(
    navController: NavController
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        HomeContent(
            navController = navController
        )
    }
}


@Composable
fun HomeContent(
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("reminder") },
                contentColor = Color.Black,
                modifier = Modifier.padding(all = 35.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ) {
            val appBarColor = MaterialTheme.colors.secondary.copy(alpha = 0.87f)

            HomeAppBar(
                backgroundColor = appBarColor,
                navController = navController,
            )

            CategoryReminder(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun HomeAppBar(
    backgroundColor: Color,
    navController: NavController,
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
        backgroundColor = backgroundColor,
        actions = {
            IconButton(onClick = { navController.navigate(route = "profile") }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null
                )
            }
            Button(
                onClick = { navController.navigate(route = "login") },
                modifier = Modifier.size(120.dp)
            ) {
                Text("Log Out")
            }
        }
    )
}

