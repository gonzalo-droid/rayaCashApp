package com.gondroid.rayacashapp.ui.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gondroid.rayacashapp.ui.home.HomeScreenRoot


@Composable
fun NavigationRoot() {
    val mainNavController = rememberNavController()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        NavHost(
            navController = mainNavController,
            startDestination = HomeScreenRoute
        ) {

            composable<HomeScreenRoute> {
                HomeScreenRoot(
                    navigateToTransactions = {
                        mainNavController.navigate(TransactionsScreenRoute)
                    }
                )
            }

        }
    }
}
