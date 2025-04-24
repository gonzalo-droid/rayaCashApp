package com.gondroid.rayacashapp.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gondroid.rayacashapp.ui.home.HomeScreenRoot


@Composable
fun NavigationRoot() {
    val mainNavController = rememberNavController()
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
